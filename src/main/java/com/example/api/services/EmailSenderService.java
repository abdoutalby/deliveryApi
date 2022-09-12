package com.example.api.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to  ){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("abderrahmentalby@gmail.com");
        message.setTo(to);
        message.setText("well done !! ");
        message.setSubject("api test");
        mailSender.send(message);
        System.out.println("Mail sent successfully...");
    }


}
