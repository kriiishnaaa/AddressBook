package com.example.AddressBookApp.Service;

import com.example.AddressBookApp.Util.EmailSenderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitmqConsumer {

    @RabbitListener(queues = "contactQueue")
    public void receiveMessage(String message) {
        System.out.println(" Received Message: " + message);
        // Here, you can call an email service to send a notification
    }
    @Autowired
    EmailSenderService emailSenderService;

    @RabbitListener(queues = "loginQueue")
    public void handleUserLogin(String message) {
        System.out.println("Received Login Event: " + message);
        emailSenderService.sendEmail(message.substring(16), "User Logged In", message);
        System.out.println((message.substring(16)));
    }

    @RabbitListener(queues = "passwordQueue")
    public void handlePasswordReset(String message) {
        System.out.println("📩 Received Password Reset Event: " + message);
        emailSenderService.sendEmail(message.substring(16), "User Reset Password", message);
    }
}