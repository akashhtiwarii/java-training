package com.example.foreignkeyannotations.integration;

import com.example.foreignkeyannotations.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/users";
    }

    @Test
    void testCreateUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Alice");
        userDTO.setEmail("alice@example.com");

        ResponseEntity<UserDTO> response = restTemplate.postForEntity(getBaseUrl(), userDTO, UserDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().getName());
    }

    @Test
    void testGetUserNotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(getBaseUrl() + "/999", String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().contains("User not found"));
    }
}

