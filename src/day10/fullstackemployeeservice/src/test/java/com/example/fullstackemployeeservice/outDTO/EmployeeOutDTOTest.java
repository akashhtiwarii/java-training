package com.example.fullstackemployeeservice.outDTO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeOutDTOTest {

    @Test
    void testNoArgsConstructor() {
        EmployeeOutDTO dto = new EmployeeOutDTO();
        assertNotNull(dto);
    }

    @Test
    void testAllArgsConstructor() {
        EmployeeOutDTO dto = new EmployeeOutDTO(
                "test@example.com",
                "Alice",
                "Smith",
                "1234567890",
                "EMPLOYEE",
                "Engineering",
                60000.0
        );

        assertEquals("test@example.com", dto.getEmail());
        assertEquals("Alice", dto.getFirstName());
        assertEquals("Smith", dto.getLastName());
        assertEquals("1234567890", dto.getPhoneNumber());
        assertEquals("EMPLOYEE", dto.getRole());
        assertEquals("Engineering", dto.getDepartment());
        assertEquals(60000.0, dto.getSalary());
    }

    @Test
    void testSettersAndGetters() {
        EmployeeOutDTO dto = new EmployeeOutDTO();

        dto.setEmail("john.doe@example.com");
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setPhoneNumber("0987654321");
        dto.setRole("ADMIN");
        dto.setDepartment("HR");
        dto.setSalary(75000.0);

        assertEquals("john.doe@example.com", dto.getEmail());
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
        assertEquals("0987654321", dto.getPhoneNumber());
        assertEquals("ADMIN", dto.getRole());
        assertEquals("HR", dto.getDepartment());
        assertEquals(75000.0, dto.getSalary());
    }

    @Test
    void testToString() {
        EmployeeOutDTO dto = new EmployeeOutDTO(
                "mark.taylor@example.com",
                "Mark",
                "Taylor",
                "5551234567",
                "HR",
                "Finance",
                85000.0
        );

        String result = dto.toString();

        assertTrue(result.contains("email='mark.taylor@example.com'"));
        assertTrue(result.contains("firstName='Mark'"));
        assertTrue(result.contains("lastName='Taylor'"));
        assertTrue(result.contains("phoneNumber='5551234567'"));
        assertTrue(result.contains("role='HR'"));
        assertTrue(result.contains("department='Finance'"));
        assertTrue(result.contains("salary=85000.0"));
    }
}
