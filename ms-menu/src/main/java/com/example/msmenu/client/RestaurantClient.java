package com.example.msmenu.client;

import com.example.msmenu.dto.client.restaurant.RestaurantResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "ms-restaurant",
        url = "${client.urls.ms-restaurant}"
)
public interface RestaurantClient {
    @GetMapping("/api/v1/restaurant/get/{id}")
    RestaurantResponse getRestaurantById(@PathVariable Long id);
}