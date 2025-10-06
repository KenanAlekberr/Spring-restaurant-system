package com.example.msrestaurant.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.RestaurantEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RestaurantConsumer {
    KafkaTemplate<String, RestaurantEvent> kafkaTemplate;

    @KafkaListener(topics = "restaurant-topic", groupId = "restaurant-group")
    public void consume(RestaurantEvent event) {
        System.out.println("✅ A message came from Kafka.: ");
        kafkaTemplate.send("restaurant-topic", event);
    }
}