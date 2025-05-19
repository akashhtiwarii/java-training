package com.example.fullstackemployeeservice.fiegnClient;

import com.example.fullstackemployeeservice.inDTO.EmailInDTO;
import com.example.fullstackemployeeservice.outDTO.EmailOutDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "emailservice", url = "http://localhost:8081", fallback = EmailFeignClientFallBack.class)
public interface EmailFeignClient {

    @PostMapping("/api/email/send")
    EmailOutDTO sendEmail(@RequestBody EmailInDTO emailRequest);
}
