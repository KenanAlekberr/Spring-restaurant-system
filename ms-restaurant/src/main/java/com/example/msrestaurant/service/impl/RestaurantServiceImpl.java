package com.example.msrestaurant.service.impl;

import com.example.msrestaurant.dao.entity.RestaurantEntity;
import com.example.msrestaurant.dao.repository.RestaurantRepository;
import com.example.msrestaurant.dto.request.CreateRestaurantRequest;
import com.example.msrestaurant.dto.request.UpdateRestaurantRequest;
import com.example.msrestaurant.dto.response.RestaurantResponse;
import com.example.msrestaurant.exception.custom.NotFoundException;
import com.example.msrestaurant.service.abstraction.RestaurantService;
import com.example.msrestaurant.util.CacheUtil;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.RestaurantEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.msrestaurant.enums.RestaurantStatus.ACTIVE;
import static com.example.msrestaurant.enums.RestaurantStatus.DELETED;
import static com.example.msrestaurant.enums.RestaurantStatus.IN_PROGRESS;
import static com.example.msrestaurant.exception.ExceptionConstants.RESTAURANT_NOT_FOUND;
import static com.example.msrestaurant.mapper.RestaurantMapper.RESTAURANT_MAPPER;
import static java.time.temporal.ChronoUnit.MINUTES;
import static lombok.AccessLevel.PRIVATE;

@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    RestaurantRepository restaurantRepository;
    CacheUtil cacheUtil;
    KafkaTemplate<String, RestaurantEvent> kafkaTemplate;

    @Override
    public RestaurantResponse createRestaurant(CreateRestaurantRequest request) {
        RestaurantEntity restaurant = RESTAURANT_MAPPER.buildRestaurantEntity(request);

        restaurantRepository.save(restaurant);

        cacheUtil.saveToCache(getKey(restaurant.getId()), restaurant, 10L, MINUTES);

        RestaurantEvent event = RESTAURANT_MAPPER.buildRestaurantEvent(restaurant);
        kafkaTemplate.send("restaurant-topic", event);

        return RESTAURANT_MAPPER.buildRestaurantResponse(restaurant);
    }

    @Override
    public RestaurantResponse getRestaurantById(Long id) {
        RestaurantEntity cachedRestaurant = cacheUtil.getBucket(getKey(id));

        if (cachedRestaurant != null) {
            if (cachedRestaurant.getRestaurantStatus() != DELETED)
                return RESTAURANT_MAPPER.buildRestaurantResponse(cachedRestaurant);
            else throw new NotFoundException(RESTAURANT_NOT_FOUND.getCode(), RESTAURANT_NOT_FOUND.getMessage());
        }

        RestaurantEntity restaurant = fetchRestaurantIfExist(id);

        cacheUtil.saveToCache(getKey(restaurant.getId()), restaurant, 10L, MINUTES);

        return RESTAURANT_MAPPER.buildRestaurantResponse(restaurant);
    }

    @Override
    public List<RestaurantResponse> getAllRestaurants() {
        List<RestaurantEntity> cachedRestaurants = cacheUtil.getBucket("RESTAURANTS:ALL");
        List<RestaurantResponse> responses = new ArrayList<>();

        if (cachedRestaurants != null && !cachedRestaurants.isEmpty()) {
            for (RestaurantEntity restaurant : cachedRestaurants) {
                if (restaurant.getRestaurantStatus() != DELETED)
                    responses.add(RESTAURANT_MAPPER.buildRestaurantResponse(restaurant));
            }

            return responses;
        }

        List<RestaurantEntity> restaurants = restaurantRepository.findAll();
        List<RestaurantEntity> activeRestaurants = new ArrayList<>();

        for (RestaurantEntity restaurant : restaurants) {
            if (restaurant.getRestaurantStatus() == ACTIVE || restaurant.getRestaurantStatus() == IN_PROGRESS) {
                activeRestaurants.add(restaurant);
                responses.add(RESTAURANT_MAPPER.buildRestaurantResponse(restaurant));
            }
        }

        cacheUtil.saveToCache("RESTAURANTS:ALL", activeRestaurants, 10L, MINUTES);

        return responses;
    }

    @Override
    public RestaurantResponse updateRestaurant(Long id, UpdateRestaurantRequest request) {
        RestaurantEntity restaurant = fetchRestaurantIfExist(id);

        RESTAURANT_MAPPER.updateRestaurant(restaurant, request);
        restaurantRepository.save(restaurant);

        cacheUtil.saveToCache(getKey(restaurant.getId()), restaurant, 10L, MINUTES);
        cacheUtil.deleteFromCache("RESTAURANTS:ALL");

        return RESTAURANT_MAPPER.buildRestaurantResponse(restaurant);
    }

    @Override
    public void deleteRestaurant(Long id) {
        RestaurantEntity restaurant = fetchRestaurantIfExist(id);

        restaurant.setRestaurantStatus(DELETED);
        restaurantRepository.save(restaurant);

        cacheUtil.deleteFromCache(getKey(id));
        cacheUtil.deleteFromCache("RESTAURANTS:ALL");
    }

    private RestaurantEntity fetchRestaurantIfExist(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(RESTAURANT_NOT_FOUND.getCode(), RESTAURANT_NOT_FOUND.getMessage()));
    }

    private String getKey(Long id) {
        return "RESTAURANT:" + id;
    }
}