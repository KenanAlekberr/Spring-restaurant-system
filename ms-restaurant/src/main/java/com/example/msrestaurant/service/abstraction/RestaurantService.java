package com.example.msrestaurant.service.abstraction;

import com.example.msrestaurant.dto.request.CreateRestaurantRequest;
import com.example.msrestaurant.dto.request.UpdateRestaurantRequest;
import com.example.msrestaurant.dto.response.RestaurantResponse;

import java.util.List;

public interface RestaurantService {
    RestaurantResponse createRestaurant(CreateRestaurantRequest request);

    RestaurantResponse getRestaurantById(Long id);

    List<RestaurantResponse> getAllRestaurants();

    RestaurantResponse updateRestaurant(Long id, UpdateRestaurantRequest request);

    void deleteRestaurant(Long id);
}