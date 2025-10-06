package com.example.msnotification.service;


import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class MailService {
    JavaMailSender mailSender;

    public void sendMail(String to, String name,String address) {
        log.info("MailService.sendMail.start to={}, name={}, address={}", to, name, address);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Restaurant Notifaction");
        message.setText("Salam,"+name+ " adli restarant "+address+" da acildi");

        mailSender.send(message);

        log.info("MailService.sendMail.end to={}, name={}, address={}", to, name, address);
    }
}