package com.example.fullstackemployeeservice.outDTO;

import java.util.Objects;

public class EmailOutDTO {
    private String message;

    public EmailOutDTO() {
    }

    public EmailOutDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailOutDTO that = (EmailOutDTO) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(message);
    }
}
