package com.example.profiles.serviceImpl;

import com.example.profiles.service.MessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
@Profile("dev")
public class EmailServiceImpl implements MessageService {

    @Value("${email.sender}")
    String from;

    @Override
    public void sendMessage(String message) {
        System.out.println("Sending Email from " + from + ": " + message);
    }
}
