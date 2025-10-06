package com.example.msnotification.kafka;

import com.example.msnotification.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.RestaurantEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Component
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RestaurantEventConsumer {
    MailService mailService;

    @KafkaListener(topics = "restaurant-topic", groupId = "restaurant-group")
    public void consume(RestaurantEvent event) {
        log.info("mail sending -> {}", event);
        mailService.sendMail("kenan.elekberov.02@gmail.com", event.getName(), event.getAddress());
        log.info("mail sent {}", event.getName());
    }
}