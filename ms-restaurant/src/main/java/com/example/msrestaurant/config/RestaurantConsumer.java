package com.example.msrestaurant.config;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.RestaurantEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Component
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RestaurantConsumer {
    KafkaTemplate<String, RestaurantEvent> kafkaTemplate;

    public void consume(RestaurantEvent event) {
        System.out.println("✅ A message came from Kafka.: ");
        kafkaTemplate.send("restaurant-topic", event);
    }
}