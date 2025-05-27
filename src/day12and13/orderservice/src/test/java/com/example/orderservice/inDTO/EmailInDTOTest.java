package com.example.orderservice.inDTO;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EmailInDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validEmailInDTO_shouldPassValidation() {
        EmailInDTO dto = new EmailInDTO("user@gmail.com", "Order Confirmed", "Thank you");

        Set<ConstraintViolation<EmailInDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void invalidEmail_shouldFailValidation() {
        EmailInDTO dto = new EmailInDTO("user@yahoo.com", "Subject", "Body");

        Set<ConstraintViolation<EmailInDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("to")));
    }

    @Test
    void blankSubject_shouldFailValidation() {
        EmailInDTO dto = new EmailInDTO("user@gmail.com", "", "Body");

        Set<ConstraintViolation<EmailInDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("subject")));
    }

    @Test
    void subjectTooLong_shouldFailValidation() {
        EmailInDTO dto = new EmailInDTO("user@gmail.com", "This subject is way too long to be valid", "Body");

        Set<ConstraintViolation<EmailInDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("subject")));
    }

    @Test
    void bodyContainsInvalidCharacters_shouldFailValidation() {
        EmailInDTO dto = new EmailInDTO("user@gmail.com", "Subject", "Body with @symbols!");

        Set<ConstraintViolation<EmailInDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("body")));
    }

    @Test
    void nullFields_shouldFailValidation() {
        EmailInDTO dto = new EmailInDTO(null, null, null);

        Set<ConstraintViolation<EmailInDTO>> violations = validator.validate(dto);
        assertEquals(3, violations.size());
    }
}

