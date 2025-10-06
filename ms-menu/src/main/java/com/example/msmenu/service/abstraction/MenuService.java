package com.example.msmenu.service.abstraction;

import com.example.msmenu.dto.client.payment.request.OrderRequest;
import com.example.msmenu.dto.client.payment.response.PaymentResponse;
import com.example.msmenu.dto.request.CreateMenuRequest;
import com.example.msmenu.dto.request.UpdateMenuRequest;
import com.example.msmenu.dto.response.MenuResponse;

import java.util.List;

public interface MenuService {
    MenuResponse createMenu(CreateMenuRequest request);

    MenuResponse getMenuById(Long id);

    List<MenuResponse> getAllMenus();

    MenuResponse updateMenu(Long id, UpdateMenuRequest request);

    void deleteMenu(Long id);

    PaymentResponse orderProduct(OrderRequest orderRequest);
}