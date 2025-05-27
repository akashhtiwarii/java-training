package com.example.orderservice.inDTO;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OrderInDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validOrderInDTO_shouldPassValidation() {
        OrderInDTO dto = new OrderInDTO();
        dto.setCustomerEmail("customer@gmail.com");
        dto.setProduct("Macbook Pro");
        dto.setQuantity(2);

        Set<ConstraintViolation<OrderInDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void nullFields_shouldFailAllValidations() {
        OrderInDTO dto = new OrderInDTO();

        Set<ConstraintViolation<OrderInDTO>> violations = validator.validate(dto);
        assertEquals(3, violations.size());
    }

    @Test
    void invalidEmailFormat_shouldFailValidation() {
        OrderInDTO dto = new OrderInDTO();
        dto.setCustomerEmail("user@yahoo.com");
        dto.setProduct("iPhone");
        dto.setQuantity(1);

        Set<ConstraintViolation<OrderInDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("customerEmail")));
    }

    @Test
    void invalidProductName_shouldFailValidation() {
        OrderInDTO dto = new OrderInDTO();
        dto.setCustomerEmail("user@gmail.com");
        dto.setProduct("##InvalidName!!");
        dto.setQuantity(1);

        Set<ConstraintViolation<OrderInDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("product")));
    }

    @Test
    void quantityLessThanOne_shouldFailValidation() {
        OrderInDTO dto = new OrderInDTO();
        dto.setCustomerEmail("user@gmail.com");
        dto.setProduct("ProductName");
        dto.setQuantity(0);

        Set<ConstraintViolation<OrderInDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("quantity")));
    }

    @Test
    void blankProduct_shouldFailValidation() {
        OrderInDTO dto = new OrderInDTO();
        dto.setCustomerEmail("user@gmail.com");
        dto.setProduct("");
        dto.setQuantity(2);

        Set<ConstraintViolation<OrderInDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("product")));
    }
}

