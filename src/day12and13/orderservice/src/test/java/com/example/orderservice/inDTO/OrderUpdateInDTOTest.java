package com.example.orderservice.inDTO;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OrderUpdateInDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validStatusValues_shouldPassValidation() {
        String[] validStatuses = {"Ordered", "Processing", "Completed"};
        for (String status : validStatuses) {
            OrderUpdateInDTO dto = new OrderUpdateInDTO();
            dto.setStatus(status);

            Set<ConstraintViolation<OrderUpdateInDTO>> violations = validator.validate(dto);
            assertTrue(violations.isEmpty(), "Status '" + status + "' should be valid");
        }
    }

    @Test
    void blankStatus_shouldFailValidation() {
        OrderUpdateInDTO dto = new OrderUpdateInDTO();
        dto.setStatus("");

        Set<ConstraintViolation<OrderUpdateInDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("status") && v.getMessage().contains("must not be blank")));
    }

    @Test
    void nullStatus_shouldFailValidation() {
        OrderUpdateInDTO dto = new OrderUpdateInDTO();
        dto.setStatus(null);

        Set<ConstraintViolation<OrderUpdateInDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("status") && v.getMessage().contains("must not be blank")));
    }

    @Test
    void invalidStatus_shouldFailValidation() {
        OrderUpdateInDTO dto = new OrderUpdateInDTO();
        dto.setStatus("Shipped");  // Not in allowed values

        Set<ConstraintViolation<OrderUpdateInDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("status") && v.getMessage().contains("must be one of")));
    }
}

