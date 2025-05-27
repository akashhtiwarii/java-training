package com.example.orderservice.outDTO;


import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderOutDTOTest {

    @Test
    void testGetterAndSetter() {
        OrderOutDTO dto = new OrderOutDTO();

        Long id = 1001L;
        String customerEmail = "test@example.com";
        String product = "Smartphone";
        Integer quantity = 2;
        LocalDateTime orderDate = LocalDateTime.of(2025, 5, 27, 10, 30);
        String status = "SHIPPED";

        dto.setId(id);
        dto.setCustomerEmail(customerEmail);
        dto.setProduct(product);
        dto.setQuantity(quantity);
        dto.setOrderDate(orderDate);
        dto.setStatus(status);

        assertEquals(id, dto.getId());
        assertEquals(customerEmail, dto.getCustomerEmail());
        assertEquals(product, dto.getProduct());
        assertEquals(quantity, dto.getQuantity());
        assertEquals(orderDate, dto.getOrderDate());
        assertEquals(status, dto.getStatus());
    }

    @Test
    void testToString() {
        OrderOutDTO dto = new OrderOutDTO();
        dto.setProduct("Laptop");
        dto.setQuantity(1);
        dto.setOrderDate(LocalDateTime.of(2025, 5, 27, 15, 45));
        dto.setStatus("PROCESSING");

        String toString = dto.toString();

        assertTrue(toString.contains("product='Laptop'"));
        assertTrue(toString.contains("quantity=1"));
        assertTrue(toString.contains("orderDate=2025-05-27T15:45"));
        assertTrue(toString.contains("status='PROCESSING'"));
    }
}

