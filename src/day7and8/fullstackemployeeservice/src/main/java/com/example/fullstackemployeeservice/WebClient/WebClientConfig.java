package com.example.fullstackemployeeservice.WebClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient emailServiceWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8081/api/email")
                .build();
    }
}
