package com.example.notificationservice.controller;


import com.example.notificationservice.inDTO.EmailInDTO;
import com.example.notificationservice.outDTO.EmailOutDTO;
import com.example.notificationservice.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmailController.class)
class EmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailService emailService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSendEmail() throws Exception {
        EmailInDTO emailInDTO = new EmailInDTO("test@example.com", "Subject", "Body");

        EmailOutDTO emailOutDTO = new EmailOutDTO();
        emailOutDTO.setMessage("Mail sent successfully");

        Mockito.when(emailService.sendEmail(any(EmailInDTO.class))).thenReturn(emailOutDTO);

        mockMvc.perform(post("/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emailInDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Mail sent successfully"));

        Mockito.verify(emailService).sendEmail(any(EmailInDTO.class));
    }
}

