package com.example.msrestaurant.mapper;

import com.example.msrestaurant.dao.entity.RestaurantEntity;
import com.example.msrestaurant.dto.request.CreateRestaurantRequest;
import com.example.msrestaurant.dto.request.UpdateRestaurantRequest;
import com.example.msrestaurant.dto.response.RestaurantResponse;
import io.micrometer.common.util.StringUtils;
import org.example.RestaurantEvent;

import static com.example.msrestaurant.enums.RestaurantStatus.ACTIVE;
import static com.example.msrestaurant.enums.RestaurantStatus.IN_PROGRESS;

public enum RestaurantMapper {
    RESTAURANT_MAPPER;

    public RestaurantEntity buildRestaurantEntity(CreateRestaurantRequest request) {
        return RestaurantEntity.builder()
                .restaurantName(request.getRestaurantName())
                .address(request.getAddress())
                .restaurantStatus(ACTIVE)
                .build();
    }

    public RestaurantResponse buildRestaurantResponse(RestaurantEntity restaurant) {
        return RestaurantResponse.builder()
                .id(restaurant.getId())
                .restaurantName(restaurant.getRestaurantName())
                .address(restaurant.getAddress())
                .restaurantStatus(restaurant.getRestaurantStatus())
                .createdAt(restaurant.getCreatedAt())
                .updatedAt(restaurant.getUpdatedAt())
                .build();
    }

    public void updateRestaurant(RestaurantEntity restaurant, UpdateRestaurantRequest request) {
        if (StringUtils.isNotEmpty(request.getRestaurantName()))
            restaurant.setRestaurantName(request.getRestaurantName());

        if (StringUtils.isNotEmpty(request.getAddress()))
            restaurant.setAddress(request.getAddress());

        restaurant.setRestaurantStatus(IN_PROGRESS);
    }

    public RestaurantEvent buildRestaurantEvent(RestaurantEntity restaurant) {
        return RestaurantEvent.builder()
                .name(restaurant.getRestaurantName())
                .address(restaurant.getAddress())
                .build();
    }
}