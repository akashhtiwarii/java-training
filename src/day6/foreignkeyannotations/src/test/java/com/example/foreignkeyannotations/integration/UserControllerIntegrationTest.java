package com.example.foreignkeyannotations.integration;

import com.example.foreignkeyannotations.dto.AddressInDTO;
import com.example.foreignkeyannotations.dto.UserInDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        AddressInDTO address1 = new AddressInDTO();
        address1.setCity("New York");
        address1.setState("NY");

        List<AddressInDTO> addressList = new ArrayList<>();
        addressList.add(address1);

        UserInDTO userInDTO = new UserInDTO();
        userInDTO.setName("John Doe");
        userInDTO.setEmail("john@example.com");
        userInDTO.setAddresses(addressList);

        ResponseEntity<UserInDTO> response = restTemplate.postForEntity(getBaseUrl(), userInDTO, UserInDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().getName());
    }

    @Test
    void testGetUserNotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(getBaseUrl() + "/999", String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("User not found"));
    }
}

