package com.example.msmenu.mapper;

import com.example.msmenu.dao.entity.MenuEntity;
import com.example.msmenu.dto.request.CreateMenuRequest;
import com.example.msmenu.dto.request.UpdateMenuRequest;
import com.example.msmenu.dto.response.MenuResponse;
import io.micrometer.common.util.StringUtils;

import java.time.LocalDateTime;

import static com.example.msmenu.enums.MenuStatus.ACTIVE;
import static com.example.msmenu.enums.MenuStatus.IN_PROGRESS;
import static java.math.BigDecimal.ZERO;

public enum MenuMapper {
    MENU_MAPPER;

    public MenuEntity buildMenuEntity(CreateMenuRequest request) {
        return MenuEntity.builder()
                .restaurantId(request.getRestaurantId())
                .productName(request.getProductName())
                .price(request.getPrice())
                .menuStatus(ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public MenuResponse buildMenuResponse(MenuEntity menuEntity) {
        return MenuResponse.builder()
                .id(menuEntity.getId())
                .restaurantId(menuEntity.getRestaurantId())
                .productName(menuEntity.getProductName())
                .price(menuEntity.getPrice())
                .menuStatus(menuEntity.getMenuStatus())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public void updateMenu(MenuEntity menuEntity, UpdateMenuRequest request) {
        if (StringUtils.isNotEmpty(request.getProductName()))
            menuEntity.setProductName(request.getProductName());

        if (menuEntity.getPrice() != null)
            menuEntity.setPrice(request.getPrice());

        menuEntity.setMenuStatus(IN_PROGRESS);
    }
}