package com.example.orderservice.entity;


import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void testGettersAndSetters() {
        Order order = new Order();

        Long id = 101L;
        String customerEmail = "user@example.com";
        String product = "Phone";
        Integer quantity = 3;
        LocalDateTime orderDate = LocalDateTime.of(2025, 5, 27, 14, 0);
        String status = "PLACED";

        order.setId(id);
        order.setCustomerEmail(customerEmail);
        order.setProduct(product);
        order.setQuantity(quantity);
        order.setOrderDate(orderDate);
        order.setStatus(status);

        assertEquals(id, order.getId());
        assertEquals(customerEmail, order.getCustomerEmail());
        assertEquals(product, order.getProduct());
        assertEquals(quantity, order.getQuantity());
        assertEquals(orderDate, order.getOrderDate());
        assertEquals(status, order.getStatus());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();

        Order order1 = new Order();
        order1.setId(1L);
        order1.setCustomerEmail("a@b.com");
        order1.setProduct("Tablet");
        order1.setQuantity(1);
        order1.setOrderDate(now);
        order1.setStatus("SHIPPED");

        Order order2 = new Order();
        order2.setId(1L);
        order2.setCustomerEmail("a@b.com");
        order2.setProduct("Tablet");
        order2.setQuantity(1);
        order2.setOrderDate(now);
        order2.setStatus("SHIPPED");

        assertEquals(order1, order2);
        assertEquals(order1.hashCode(), order2.hashCode());
    }

    @Test
    void testNotEqualsDifferentFields() {
        Order order1 = new Order();
        order1.setId(1L);

        Order order2 = new Order();
        order2.setId(2L);  // Different ID

        assertNotEquals(order1, order2);
    }

    @Test
    void testToString() {
        Order order = new Order();
        order.setId(5L);
        order.setCustomerEmail("test@demo.com");
        order.setProduct("Monitor");
        order.setQuantity(2);
        order.setOrderDate(LocalDateTime.of(2025, 5, 27, 16, 30));
        order.setStatus("DELIVERED");

        String output = order.toString();

        assertTrue(output.contains("id=5"));
        assertTrue(output.contains("customerEmail='test@demo.com'"));
        assertTrue(output.contains("product='Monitor'"));
        assertTrue(output.contains("quantity=2"));
        assertTrue(output.contains("orderDate=2025-05-27T16:30"));
        assertTrue(output.contains("status='DELIVERED'"));
    }
}

