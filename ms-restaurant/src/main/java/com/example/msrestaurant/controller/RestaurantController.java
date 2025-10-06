package com.example.msrestaurant.controller;

import com.example.msrestaurant.dto.request.CreateRestaurantRequest;
import com.example.msrestaurant.dto.request.UpdateRestaurantRequest;
import com.example.msrestaurant.dto.response.RestaurantResponse;
import com.example.msrestaurant.service.abstraction.RestaurantService;
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
@RequestMapping("/api/v1/restaurant")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RestaurantController {
    RestaurantService restaurantService;

    @PostMapping("/post")
    @ResponseStatus(CREATED)
    public RestaurantResponse createRestaurant(@Valid @RequestBody CreateRestaurantRequest request) {
        return restaurantService.createRestaurant(request);
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(OK)
    public RestaurantResponse getRestaurantById(@PathVariable Long id) {
        return restaurantService.getRestaurantById(id);
    }

    @GetMapping("/getAllRestaurants")
    @ResponseStatus(OK)
    public List<RestaurantResponse> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @PutMapping("/put/{id}")
    @ResponseStatus(NO_CONTENT)
    public RestaurantResponse updateRestaurant(@PathVariable Long id, @Valid @RequestBody UpdateRestaurantRequest request) {
        return restaurantService.updateRestaurant(id, request);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
    }
}