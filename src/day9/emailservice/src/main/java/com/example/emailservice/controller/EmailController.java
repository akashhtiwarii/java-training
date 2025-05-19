package com.example.emailservice.controller;

import com.example.emailservice.dto.EmailInDTO;
import com.example.emailservice.dto.EmailOutDTO;
import com.example.emailservice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<EmailOutDTO> sendEmail(@RequestBody EmailInDTO emailInDTO) {
        EmailOutDTO response = emailService.sendEmail(emailInDTO);
        return ResponseEntity.ok(response);
    }

}
