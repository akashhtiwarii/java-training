package com.example.fullstackemployeeservice.inDTO;

import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeInDTOTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testNoArgsConstructor() {
        EmployeeInDTO dto = new EmployeeInDTO();
        assertNotNull(dto);
    }

    @Test
    void testAllArgsConstructorAndGetters() {
        EmployeeInDTO dto = new EmployeeInDTO(
                "test@gmail.com",
                "John",
                "Doe",
                "+1234567890",
                "EMPLOYEE",
                "Engineering",
                50000.0
        );

        assertEquals("test@gmail.com", dto.getEmail());
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
        assertEquals("+1234567890", dto.getPhoneNumber());
        assertEquals("EMPLOYEE", dto.getRole());
        assertEquals("Engineering", dto.getDepartment());
        assertEquals(50000.0, dto.getSalary());
    }

    @Test
    void testSetters() {
        EmployeeInDTO dto = new EmployeeInDTO();
        dto.setEmail("valid@gmail.com");
        dto.setFirstName("Alice");
        dto.setLastName("Smith");
        dto.setPhoneNumber("9876543210");
        dto.setRole("HR");
        dto.setDepartment("Human Resources");
        dto.setSalary(45000.0);

        assertEquals("valid@gmail.com", dto.getEmail());
        assertEquals("Alice", dto.getFirstName());
        assertEquals("Smith", dto.getLastName());
        assertEquals("9876543210", dto.getPhoneNumber());
        assertEquals("HR", dto.getRole());
        assertEquals("Human Resources", dto.getDepartment());
        assertEquals(45000.0, dto.getSalary());
    }

    @Test
    void testToStringContainsFields() {
        EmployeeInDTO dto = new EmployeeInDTO("john@gmail.com", "John", "Smith", "1234567890", "ADMIN", "Finance", 100000.0);
        String str = dto.toString();
        assertTrue(str.contains("john@gmail.com"));
        assertTrue(str.contains("John"));
        assertTrue(str.contains("Smith"));
        assertTrue(str.contains("1234567890"));
        assertTrue(str.contains("ADMIN"));
        assertTrue(str.contains("Finance"));
        assertTrue(str.contains("100000.0"));
    }

    @Test
    void testValidDTO() {
        EmployeeInDTO dto = new EmployeeInDTO("user@gmail.com", "Jane", "Doe", "+11234567890", "ADMIN", "IT", 70000.0);
        Set<ConstraintViolation<EmployeeInDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidEmail() {
        EmployeeInDTO dto = new EmployeeInDTO("invalid-email", "Jane", "Doe", "+11234567890", "ADMIN", "IT", 70000.0);
        Set<ConstraintViolation<EmployeeInDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void testInvalidFirstName() {
        EmployeeInDTO dto = new EmployeeInDTO("user@gmail.com", "J", "Doe", "+11234567890", "ADMIN", "IT", 70000.0);
        Set<ConstraintViolation<EmployeeInDTO>> violations = validator.validate(dto);
        assertFalse(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("lastName")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("firstName")));
    }

    @Test
    void testInvalidLastName() {
        EmployeeInDTO dto = new EmployeeInDTO("user@gmail.com", "Jane", "", "+11234567890", "ADMIN", "IT", 70000.0);
        Set<ConstraintViolation<EmployeeInDTO>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("lastName")));
    }

    @Test
    void testInvalidPhoneNumber() {
        EmployeeInDTO dto = new EmployeeInDTO("user@gmail.com", "Jane", "Doe", "abc123", "ADMIN", "IT", 70000.0);
        Set<ConstraintViolation<EmployeeInDTO>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("phoneNumber")));
    }

    @Test
    void testInvalidRole() {
        EmployeeInDTO dto = new EmployeeInDTO("user@gmail.com", "Jane", "Doe", "+11234567890", "CEO", "IT", 70000.0);
        Set<ConstraintViolation<EmployeeInDTO>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("role")));
    }

    @Test
    void testInvalidDepartment() {
        EmployeeInDTO dto = new EmployeeInDTO("user@gmail.com", "Jane", "Doe", "+11234567890", "HR", "", 70000.0);
        Set<ConstraintViolation<EmployeeInDTO>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("department")));
    }

    @Test
    void testInvalidSalary() {
        EmployeeInDTO dto = new EmployeeInDTO("user@gmail.com", "Jane", "Doe", "+11234567890", "HR", "Admin", -100.0);
        Set<ConstraintViolation<EmployeeInDTO>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("salary")));
    }

    @Test
    void testNullSalary() {
        EmployeeInDTO dto = new EmployeeInDTO("user@gmail.com", "Jane", "Doe", "+11234567890", "HR", "Admin", null);
        Set<ConstraintViolation<EmployeeInDTO>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("salary")));
    }
}
