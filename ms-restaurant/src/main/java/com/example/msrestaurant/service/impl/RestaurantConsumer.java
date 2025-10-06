package com.example.msrestaurant.service.impl;

import com.example.msrestaurant.dao.entity.RestaurantEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RestaurantConsumer {
    @KafkaListener(topics = "restaurant-topic", groupId = "restaurant-group")
    public void consume(RestaurantEntity restaurant) {
        System.out.println("✅ A message came from Kafka.: " + restaurant.getRestaurantName());
    }
}