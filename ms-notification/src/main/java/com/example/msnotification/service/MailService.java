//package com.example.msnotification.service;
//
//
//public class MailService {
//    private final JavaMailSender mailSender;
//
//    public void sendMail(String to, String name,String address) {
//        log.info("MailService.sendMail.start to={}, name={}, address={}", to, name, address);
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject("Restaurant Notifaction");
//        message.setText("Salam,"+name+ " adli restarant "+address+" da acildi");
//        mailSender.send(message);
//        log.info("MailService.sendMail.end to={}, name={}, address={}", to, name, address);
//    }
//}