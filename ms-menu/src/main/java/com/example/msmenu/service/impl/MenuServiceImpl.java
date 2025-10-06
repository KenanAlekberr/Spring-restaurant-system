package com.example.msmenu.service.impl;

import com.example.msmenu.client.PaymentClient;
import com.example.msmenu.client.RestaurantClient;
import com.example.msmenu.dao.entity.MenuEntity;
import com.example.msmenu.dao.repository.MenuRepository;
import com.example.msmenu.dto.client.payment.request.OrderRequest;
import com.example.msmenu.dto.client.payment.request.PaymentRequest;
import com.example.msmenu.dto.client.payment.response.PaymentResponse;
import com.example.msmenu.dto.client.restaurant.RestaurantResponse;
import com.example.msmenu.dto.request.CreateMenuRequest;
import com.example.msmenu.dto.request.UpdateMenuRequest;
import com.example.msmenu.dto.response.MenuResponse;
import com.example.msmenu.exception.custom.NotFoundException;
import com.example.msmenu.service.abstraction.MenuService;
import com.example.msmenu.util.CacheUtil;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.example.msmenu.enums.MenuStatus.ACTIVE;
import static com.example.msmenu.enums.MenuStatus.DELETED;
import static com.example.msmenu.enums.MenuStatus.IN_PROGRESS;
import static com.example.msmenu.exception.ExceptionConstants.MENU_NOT_FOUND;
import static com.example.msmenu.exception.ExceptionConstants.RESTAURANT_NOT_FOUND;
import static com.example.msmenu.mapper.MenuMapper.MENU_MAPPER;
import static java.math.BigDecimal.ZERO;
import static java.time.temporal.ChronoUnit.MINUTES;
import static lombok.AccessLevel.PRIVATE;

@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    MenuRepository menuRepository;
    RestaurantClient restaurantClient;
    CacheUtil cacheUtil;
    PaymentClient paymentClient;

    @Override
    public MenuResponse createMenu(CreateMenuRequest request) {
        RestaurantResponse restaurantResponse;

        try {
            restaurantResponse = restaurantClient.getRestaurantById(request.getRestaurantId());
        } catch (FeignException.NotFound ex) {
            throw new NotFoundException(RESTAURANT_NOT_FOUND.getCode(),
                    "Customer not found with id: " + request.getRestaurantId());
        }

        MenuEntity menu = MENU_MAPPER.buildMenuEntity(request);
        menuRepository.save(menu);

        cacheUtil.saveToCache(getKey(menu.getId()), menu, 10L, MINUTES);

        return MENU_MAPPER.buildMenuResponse(menu);
    }

    @Override
    public MenuResponse getMenuById(Long id) {
        MenuEntity cachedMenu = cacheUtil.getBucket(getKey(id));

        if (cachedMenu != null) {
            if (cachedMenu.getMenuStatus() != DELETED) {
                System.out.println("🔴 Read from Redis!");
                return MENU_MAPPER.buildMenuResponse(cachedMenu);
            } else {
                throw new NotFoundException(MENU_NOT_FOUND.getCode(), MENU_NOT_FOUND.getMessage());
            }
        }

        MenuEntity menuEntity = fetchMenuIfExist(id);

        if (menuEntity.getMenuStatus() == DELETED)
            throw new NotFoundException(MENU_NOT_FOUND.getCode(), MENU_NOT_FOUND.getMessage());

        cacheUtil.saveToCache(getKey(menuEntity.getId()), menuEntity, 10L, MINUTES);
        System.out.println("🟢 Read from DB and written to Redis!");

        return MENU_MAPPER.buildMenuResponse(menuEntity);
    }

    @Override
    public List<MenuResponse> getAllMenus() {
        List<MenuEntity> cachedMenu = cacheUtil.getBucket("MENUS:ALL");
        List<MenuResponse> cachedResponses = new ArrayList<>();

        if (cachedMenu != null && cachedMenu.isEmpty()) {
            System.out.println("🔴 Read ALL from Redis!");

            for (MenuEntity menu : cachedMenu) {
                if (menu.getMenuStatus() == ACTIVE || menu.getMenuStatus() == IN_PROGRESS)
                    cachedResponses.add(MENU_MAPPER.buildMenuResponse(menu));
            }

            return cachedResponses;
        }

        List<MenuEntity> menuEntities = menuRepository.findAll();
        List<MenuResponse> menuResponses = new ArrayList<>();

        for (MenuEntity menuEntity : menuEntities) {
            if (menuEntity.getMenuStatus() == ACTIVE || menuEntity.getMenuStatus() == IN_PROGRESS)
                menuResponses.add(MENU_MAPPER.buildMenuResponse(menuEntity));
        }

        return menuResponses;
    }

    @Override
    public MenuResponse updateMenu(Long id, UpdateMenuRequest request) {
        MenuEntity menuEntity = fetchMenuIfExist(id);

        if (menuEntity.getMenuStatus() == DELETED)
            throw new NotFoundException(MENU_NOT_FOUND.getCode(), MENU_NOT_FOUND.getMessage());

        MENU_MAPPER.updateMenu(menuEntity, request);
        menuRepository.save(menuEntity);

        cacheUtil.saveToCache(getKey(menuEntity.getId()), menuEntity, 10L, MINUTES);
        cacheUtil.deleteFromCache("RESTAURANTS:ALL");

        return MENU_MAPPER.buildMenuResponse(menuEntity);
    }

    @Override
    public void deleteMenu(Long id) {
        MenuEntity menuEntity = fetchMenuIfExist(id);

        if (menuEntity.getMenuStatus() == DELETED)
            throw new NotFoundException(MENU_NOT_FOUND.getCode(), MENU_NOT_FOUND.getMessage());

        menuEntity.setMenuStatus(DELETED);
        menuRepository.save(menuEntity);

        cacheUtil.deleteFromCache(getKey(id));
        cacheUtil.deleteFromCache("RESTAURANTS:ALL");
    }

    @Override
    public PaymentResponse orderProduct(OrderRequest orderRequest) {
        List<MenuEntity> menuEntities = menuRepository.findAllById(orderRequest.getMenuItemIds());

        if (menuEntities.isEmpty())
            throw new NotFoundException(MENU_NOT_FOUND.getCode(), MENU_NOT_FOUND.getMessage());

        BigDecimal totalPrice = ZERO;

        for (MenuEntity menu : menuEntities) {
            totalPrice = totalPrice.add(menu.getPrice());
        }

        PaymentRequest request = PaymentRequest.builder()
                .userId(orderRequest.getUserId())
                .amount(totalPrice)
                .build();

        return paymentClient.makePayment(request);
    }

    private MenuEntity fetchMenuIfExist(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MENU_NOT_FOUND.getCode(), MENU_NOT_FOUND.getMessage()));
    }

    private String getKey(Long id) {
        return "MENU:" + id;
    }
}