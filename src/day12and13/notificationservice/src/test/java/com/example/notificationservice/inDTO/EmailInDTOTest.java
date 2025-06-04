package com.example.notificationservice.inDTO;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class EmailInDTOTest {

    @Test
    void testNoArgsConstructorAndSettersAndGetters() {
        EmailInDTO dto = new EmailInDTO();

        dto.setTo("test@example.com");
        dto.setSubject("Test Subject");
        dto.setBody("Test Body");

        assertEquals("test@example.com", dto.getTo());
        assertEquals("Test Subject", dto.getSubject());
        assertEquals("Test Body", dto.getBody());
    }

    @Test
    void testAllArgsConstructor() {
        EmailInDTO dto = new EmailInDTO("to@example.com", "Subject", "Body");

        assertEquals("to@example.com", dto.getTo());
        assertEquals("Subject", dto.getSubject());
        assertEquals("Body", dto.getBody());
    }

    @Test
    void testEqualsAndHashCode() {
        EmailInDTO dto1 = new EmailInDTO("a@b.com", "Subj", "Body1");
        EmailInDTO dto2 = new EmailInDTO("a@b.com", "Subj", "Body1");
        EmailInDTO dto3 = new EmailInDTO("x@y.com", "Subj", "Body1");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }

    @Test
    void testToString() {
        EmailInDTO dto = new EmailInDTO("to@domain.com", "Hello", "BodyText");
        String str = dto.toString();

        assertTrue(str.contains("to='to@domain.com'"));
        assertTrue(str.contains("subject='Hello'"));
        assertTrue(str.contains("body='BodyText'"));
    }
}
