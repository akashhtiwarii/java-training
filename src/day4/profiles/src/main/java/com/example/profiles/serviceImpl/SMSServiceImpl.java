package com.example.profiles.serviceImpl;

import com.example.profiles.service.MessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
@Profile("test")
public class SMSServiceImpl implements MessageService {

    @Value("${sms.sender}")
    String from;

    @Override
    public void sendMessage(String message) {
        System.out.println("Sending SMS from " + from + ": " + message);
    }
}
