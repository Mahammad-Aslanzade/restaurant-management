package com.example.restaurantmanagement.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    public void postEmail(String adress, String subject, String body) {
        log.info("ACTION.postEmail.start adress : {} | subject : {}", adress, subject);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(System.getenv("mail_username"));
        message.setTo(adress);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}