package com.example.emailservice.service;

import com.example.emailservice.dto.EmailInDTO;
import com.example.emailservice.dto.EmailOutDTO;

public interface EmailService {
    public EmailOutDTO sendEmail(EmailInDTO emailInDTO);
}
