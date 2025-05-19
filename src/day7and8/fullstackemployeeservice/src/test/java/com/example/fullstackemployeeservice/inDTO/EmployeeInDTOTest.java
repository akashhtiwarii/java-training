package com.example.fullstackemployeeservice.inDTO;

import jakarta.validation.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeInDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidatorInstance() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testGettersAndSetters() {
        EmployeeInDTO dto = new EmployeeInDTO();

        dto.setEmail("test@gmail.com");
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setPhoneNumber("+12345678901");
        dto.setRole("ADMIN");

        assertEquals("test@gmail.com", dto.getEmail());
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
        assertEquals("+12345678901", dto.getPhoneNumber());
        assertEquals("ADMIN", dto.getRole());
    }

    @Test
    void testParameterizedConstructor() {
        EmployeeInDTO dto = new EmployeeInDTO("jane@gmail.com", "Jane", "Smith", "1234567890", "HR");

        assertEquals("jane@gmail.com", dto.getEmail());
        assertEquals("Jane", dto.getFirstName());
        assertEquals("Smith", dto.getLastName());
        assertEquals("1234567890", dto.getPhoneNumber());
        assertEquals("HR", dto.getRole());
    }

    @Test
    void testValidationSuccess() {
        EmployeeInDTO dto = new EmployeeInDTO("valid@gmail.com", "Alice", "O'Neil", "+12345678901", "EMPLOYEE");

        Set<ConstraintViolation<EmployeeInDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testValidationFailures() {
        EmployeeInDTO dto = new EmployeeInDTO("invalidemail", "J", "", "phone", "INVALID_ROLE");

        Set<ConstraintViolation<EmployeeInDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());

        Set<String> messages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(java.util.stream.Collectors.toSet());

        assertTrue(messages.contains("Email should be valid") || messages.contains("Only Gmail addresses are accepted"));
        assertTrue(messages.contains("First name should be valid"));
        assertTrue(messages.contains("Last name is required") || messages.contains("Last name should be valid"));
        assertTrue(messages.contains("Phone number should be valid"));
        assertTrue(messages.contains("Role must be one of: ADMIN, EMPLOYEE, HR"));
    }

    @Test
    void testToString() {
        EmployeeInDTO dto = new EmployeeInDTO("user@gmail.com", "Bob", "Marley", "1234567890", "HR");
        String expected = "EmployeeInDTO{email='user@gmail.com', firstName='Bob', lastName='Marley', phoneNumber='1234567890', role='HR'}";
        assertEquals(expected, dto.toString());
    }
}

