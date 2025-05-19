package com.example.fullstackemployeeservice.exception;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    @Test
    void defaultConstructor_ShouldSetTimestampToNow() {
        ErrorResponse errorResponse = new ErrorResponse();

        assertNotNull(errorResponse.getTimestamp());

        LocalDateTime before = LocalDateTime.now().minusSeconds(1);
        LocalDateTime after = LocalDateTime.now().plusSeconds(1);

        // timestamp should be within a reasonable range (between before and after)
        assertTrue(errorResponse.getTimestamp().isAfter(before));
        assertTrue(errorResponse.getTimestamp().isBefore(after));
    }

    @Test
    void parameterizedConstructor_ShouldSetStatusAndMessageAndTimestamp() {
        int status = 404;
        String message = "Not Found";

        ErrorResponse errorResponse = new ErrorResponse(status, message);

        assertEquals(status, errorResponse.getStatus());
        assertEquals(message, errorResponse.getMessage());

        assertNotNull(errorResponse.getTimestamp());

        LocalDateTime before = LocalDateTime.now().minusSeconds(1);
        LocalDateTime after = LocalDateTime.now().plusSeconds(1);

        assertTrue(errorResponse.getTimestamp().isAfter(before));
        assertTrue(errorResponse.getTimestamp().isBefore(after));
    }

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        ErrorResponse errorResponse = new ErrorResponse();

        int status = 500;
        String message = "Internal Server Error";
        LocalDateTime timestamp = LocalDateTime.of(2023, 5, 18, 12, 0, 0);

        errorResponse.setStatus(status);
        errorResponse.setMessage(message);
        errorResponse.setTimestamp(timestamp);

        assertEquals(status, errorResponse.getStatus());
        assertEquals(message, errorResponse.getMessage());
        assertEquals(timestamp, errorResponse.getTimestamp());
    }
}
