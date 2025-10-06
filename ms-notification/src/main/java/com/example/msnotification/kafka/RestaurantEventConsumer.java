//package com.example.msnotification.kafka;
//
//public class RestaurantEventConsumer {
//    MailService mailService;
//    @KafkaListener(topics = "restaurant-events", groupId = "restaurant-service")
//    public void consume(RestaurantEvent event) {
//        log.info("mail sending -> {}", event);
//        mailService.sendMail("someoneother@gmail.com",event.getName(), event.getAddress());
//        log.info("mail sended {}", event.getName());
//    }
//}