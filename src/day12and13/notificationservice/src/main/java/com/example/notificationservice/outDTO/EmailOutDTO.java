package com.example.notificationservice.outDTO;

/**
 * Data Transfer Object representing the response after sending an email.
 * Contains a message indicating the result of the email sending operation.
 */
public class EmailOutDTO {
    /**
     * Response message, typically indicating success or failure status.
     */
    private String message;

    /**
     * Gets the response message.
     *
     * @return the response message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the response message.
     *
     * @param message the response message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
