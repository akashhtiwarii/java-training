package com.example.notificationservice.serviceImpl;


import com.example.notificationservice.exception.EmailServiceException;
import com.example.notificationservice.inDTO.EmailInDTO;
import com.example.notificationservice.outDTO.EmailOutDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailServiceImplTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailServiceImpl emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendEmail_Success() {
        EmailInDTO emailInDTO = new EmailInDTO("recipient@example.com", "Test Subject", "Test Body");

        // No exception is thrown by javaMailSender.send(), so success case
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

        EmailOutDTO response = emailService.sendEmail(emailInDTO);

        assertNotNull(response);
        assertEquals("Mail sent successfully", response.getMessage());

        // Verify that javaMailSender.send was called once
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void testSendEmail_Failure() {
        EmailInDTO emailInDTO = new EmailInDTO("recipient@example.com", "Test Subject", "Test Body");

        // Simulate exception when sending mail
        doThrow(new RuntimeException("SMTP server not reachable"))
                .when(javaMailSender).send(any(SimpleMailMessage.class));

        EmailServiceException exception = assertThrows(EmailServiceException.class, () -> {
            emailService.sendEmail(emailInDTO);
        });

        assertEquals("Failed to send email", exception.getMessage());

        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}

