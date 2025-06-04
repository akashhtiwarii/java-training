package com.example.notificationservice.outDTO;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class EmailOutDTOTest {

    @Test
    void testGetSetMessage() {
        EmailOutDTO dto = new EmailOutDTO();
        dto.setMessage("Email sent successfully");
        assertEquals("Email sent successfully", dto.getMessage());
    }

    @Test
    void testEqualsAndHashCode() {
        EmailOutDTO dto1 = new EmailOutDTO();
        dto1.setMessage("Success");

        EmailOutDTO dto2 = new EmailOutDTO();
        dto2.setMessage("Success");

        EmailOutDTO dto3 = new EmailOutDTO();
        dto3.setMessage("Failure");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1.hashCode(), dto3.hashCode());

        assertNotEquals(dto1, null);
        assertNotEquals(dto1, "Some String");
    }

    @Test
    void testToString() {
        EmailOutDTO dto = new EmailOutDTO();
        dto.setMessage("Test message");
        String str = dto.toString();

        assertTrue(str.contains("message='Test message'") || str.contains("message=\"Test message\"") || str.contains("message=Test message"));
    }
}

