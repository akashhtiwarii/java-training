package com.example.foreignkeyannotations.integration;

import com.example.foreignkeyannotations.dto.AddressInDTO;
import com.example.foreignkeyannotations.dto.UserInDTO;
import com.example.foreignkeyannotations.dto.UserOutDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

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

        ResponseEntity<UserOutDTO> response = restTemplate.postForEntity(getBaseUrl(), userInDTO, UserOutDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getName());
    }

    @Test
    void testCreateUser_missingFields() {
        UserInDTO userInDTO = new UserInDTO();
        userInDTO.setName("");
        userInDTO.setEmail("");
        userInDTO.setAddresses(new ArrayList<>());

        ResponseEntity<String> response = restTemplate.postForEntity(getBaseUrl(), userInDTO, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Invalid Request"));
    }

    @Test
    void testGetUserNotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(getBaseUrl() + "/9999", String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("User not found"));
    }

    @Test
    void testGetAllUsers() {
        AddressInDTO address = new AddressInDTO();
        address.setCity("Los Angeles");
        address.setState("CA");

        UserInDTO userInDTO = new UserInDTO();
        userInDTO.setName("Jane Doe");
        userInDTO.setEmail("jane@example.com");
        userInDTO.setAddresses(List.of(address));

        restTemplate.postForEntity(getBaseUrl(), userInDTO, UserOutDTO.class);

        ResponseEntity<UserOutDTO[]> response = restTemplate.getForEntity(getBaseUrl(), UserOutDTO[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length >= 1);
    }

    @Test
    void testGetUserById_success() {
        AddressInDTO address = new AddressInDTO();
        address.setCity("Chicago");
        address.setState("IL");

        UserInDTO userInDTO = new UserInDTO();
        userInDTO.setName("Bob Smith");
        userInDTO.setEmail("bob@example.com");
        userInDTO.setAddresses(List.of(address));

        ResponseEntity<UserOutDTO> postResponse = restTemplate.postForEntity(getBaseUrl(), userInDTO, UserOutDTO.class);
        Long userId = Objects.requireNonNull(postResponse.getBody()).getId();

        ResponseEntity<UserOutDTO> getResponse = restTemplate.getForEntity(getBaseUrl() + "/" + userId, UserOutDTO.class);

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals("Bob Smith", getResponse.getBody().getName());
    }
}
