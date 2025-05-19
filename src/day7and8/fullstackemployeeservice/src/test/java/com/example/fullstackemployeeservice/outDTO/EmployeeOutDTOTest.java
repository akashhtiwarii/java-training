package com.example.fullstackemployeeservice.outDTO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeOutDTOTest {

    @Test
    void testGettersAndSetters() {
        EmployeeOutDTO dto = new EmployeeOutDTO();

        dto.setEmail("test@example.com");
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setPhoneNumber("1234567890");
        dto.setRole("EMPLOYEE");

        assertEquals("test@example.com", dto.getEmail());
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
        assertEquals("1234567890", dto.getPhoneNumber());
        assertEquals("EMPLOYEE", dto.getRole());
    }

    @Test
    void testParameterizedConstructor() {
        EmployeeOutDTO dto = new EmployeeOutDTO(
                "jane@example.com",
                "Jane",
                "Smith",
                "0987654321",
                "ADMIN"
        );

        assertEquals("jane@example.com", dto.getEmail());
        assertEquals("Jane", dto.getFirstName());
        assertEquals("Smith", dto.getLastName());
        assertEquals("0987654321", dto.getPhoneNumber());
        assertEquals("ADMIN", dto.getRole());
    }

    @Test
    void testToString() {
        EmployeeOutDTO dto = new EmployeeOutDTO(
                "someone@example.com",
                "Sam",
                "Wilson",
                "5555555555",
                "HR"
        );

        String expected = "EmployeeOutDTO{" +
                ", email='someone@example.com'" +
                ", firstName='Sam'" +
                ", lastName='Wilson'" +
                ", phoneNumber='5555555555'" +
                ", role='HR'" +
                '}';

        assertEquals(expected, dto.toString());
    }
}

