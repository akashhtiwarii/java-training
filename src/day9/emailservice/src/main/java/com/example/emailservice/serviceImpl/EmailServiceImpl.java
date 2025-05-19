package com.example.emailservice.serviceImpl;

import com.example.emailservice.dto.EmailInDTO;
import com.example.emailservice.dto.EmailOutDTO;
import com.example.emailservice.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public EmailOutDTO sendEmail(EmailInDTO emailInDTO) {
        EmailOutDTO emailOutDTO =  new EmailOutDTO();

        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setReplyTo("employeeservice@gmail.com");
//            message.setTo(emailInDTO.getTo());
//            message.setSubject(emailInDTO.getSubject());
//            message.setText(emailInDTO.getBody());
//
//            javaMailSender.send(message);

            emailOutDTO.setMessage("The following message with subject: " + emailInDTO.getSubject() + " " +
                    "has been sent to " + emailInDTO.getTo() + ": " + emailInDTO.getBody());
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }

        emailOutDTO.setMessage("The following message with subject: " + emailInDTO.getSubject() + " " +
                "has been sent to " + emailInDTO.getTo() + ": " + emailInDTO.getBody());

        return emailOutDTO;
    }


}
