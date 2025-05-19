package com.example.fullstackemployeeservice.fiegnClient;

import com.example.fullstackemployeeservice.inDTO.EmailInDTO;
import com.example.fullstackemployeeservice.outDTO.EmailOutDTO;
import org.springframework.stereotype.Component;

@Component
public class EmailFeignClientFallBack implements EmailFeignClient{
    @Override
    public EmailOutDTO sendEmail(EmailInDTO emailRequest) {
        EmailOutDTO fallbackResponse = new EmailOutDTO();
        fallbackResponse.setMessage("Failed to send email: fallback response");
        return fallbackResponse;
    }
}
