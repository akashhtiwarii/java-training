package com.example.fullstackemployeeservice.kafka;

import com.example.fullstackemployeeservice.outDTO.EmployeeOutDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmployeeKafkaProducer {

    private static final String TOPIC = "employee-events";

    private final KafkaTemplate<String, EmployeeOutDTO> kafkaTemplate;

    public EmployeeKafkaProducer(KafkaTemplate<String, EmployeeOutDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEmployeeCreatedEvent(EmployeeOutDTO employee) {
        System.out.println("Sending employee event: " + employee);
        kafkaTemplate.send(TOPIC, employee);
    }
}

