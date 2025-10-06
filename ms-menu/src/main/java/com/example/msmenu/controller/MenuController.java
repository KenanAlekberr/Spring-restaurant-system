package com.example.msmenu.controller;

import com.example.msmenu.dto.client.payment.request.OrderRequest;
import com.example.msmenu.dto.client.payment.response.PaymentResponse;
import com.example.msmenu.dto.request.CreateMenuRequest;
import com.example.msmenu.dto.request.UpdateMenuRequest;
import com.example.msmenu.dto.response.MenuResponse;
import com.example.msmenu.service.abstraction.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/menu")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MenuController {
    MenuService menuService;

    @PostMapping("/post")
    @ResponseStatus(CREATED)
    public MenuResponse createMenu(@Valid @RequestBody CreateMenuRequest request) {
        return menuService.createMenu(request);
    }

    @PostMapping("/order")
    @ResponseStatus(OK)
    public PaymentResponse orderProduct(OrderRequest orderRequest) {
        return menuService.orderProduct(orderRequest);
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(OK)
    public MenuResponse getMenuById(@PathVariable Long id) {
        return menuService.getMenuById(id);
    }

    @GetMapping("/getAllMenus")
    @ResponseStatus(OK)
    public List<MenuResponse> getAllMenus() {
        return menuService.getAllMenus();
    }

    @PutMapping("/put/{id}")
    @ResponseStatus(OK)
    public MenuResponse updateMenu(@PathVariable Long id, @Valid @RequestBody UpdateMenuRequest request) {
        return menuService.updateMenu(id, request);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
    }
}