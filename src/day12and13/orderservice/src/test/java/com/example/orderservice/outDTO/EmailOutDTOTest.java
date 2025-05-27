package com.example.orderservice.outDTO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailOutDTOTest {

    @Test
    void testGetterAndSetter() {
        EmailOutDTO dto = new EmailOutDTO();
        String expectedMessage = "Email sent successfully";

        dto.setMessage(expectedMessage);

        assertEquals(expectedMessage, dto.getMessage(), "Message should be correctly set and retrieved");
    }

    @Test
    void testToString() {
        EmailOutDTO dto = new EmailOutDTO();
        dto.setMessage("Test email message");

        String expected = "EmailOutDTO{message='Test email message'}";
        assertEquals(expected, dto.toString(), "toString should return correct representation");
    }
}
