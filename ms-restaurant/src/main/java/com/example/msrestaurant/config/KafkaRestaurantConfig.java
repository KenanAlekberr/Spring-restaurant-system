package com.example.msrestaurant.config;

import com.example.msrestaurant.dao.entity.RestaurantEntity;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.RestaurantEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;
import static org.springframework.kafka.support.serializer.JsonSerializer.ADD_TYPE_INFO_HEADERS;

@Configuration
public class KafkaRestaurantConfig {
    @Bean
    public ProducerFactory<String, RestaurantEvent> restaurantFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(BOOTSTRAP_SERVERS_CONFIG, System.getenv().getOrDefault("SPRING_KAFKA_BOOTSTRAP_SERVERS", "localhost:9092"));
        config.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(ADD_TYPE_INFO_HEADERS, false);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, RestaurantEvent> kafkaTemplate() {
        return new KafkaTemplate<>(restaurantFactory());
    }
}