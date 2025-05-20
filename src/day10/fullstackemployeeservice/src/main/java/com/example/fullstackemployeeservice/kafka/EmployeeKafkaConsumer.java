package com.example.fullstackemployeeservice.kafka;

import com.example.fullstackemployeeservice.outDTO.EmployeeOutDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmployeeKafkaConsumer {

    @KafkaListener(topics = "employee-events", groupId = "notification-service-group")
    public void consumeEmployeeEvent(EmployeeOutDTO employee) {
        System.out.println("Received employee event: " + employee);
    }
}