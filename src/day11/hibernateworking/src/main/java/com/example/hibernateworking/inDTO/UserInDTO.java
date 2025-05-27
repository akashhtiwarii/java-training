package com.example.hibernateworking.inDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Data Transfer Object (DTO) for receiving user input data.
 * <p>
 * This class is used to accept user data from the client when creating or updating a user.
 * It includes validation constraints to ensure data integrity.
 * </p>
 */
public class UserInDTO {

    /**
     * The name of the user.
     * <p>
     * Must not be blank and must match the specified regex pattern which allows
     * alphabetic characters, spaces, hyphens, and apostrophes. Minimum 2 and maximum 30 characters.
     * </p>
     */
    @NotBlank(message = "First name is required")
    @Pattern(regexp = "^[A-Za-z\\s-']{2,30}$", message = "First name should be valid")
    private String name;

    /**
     * The email address of the user.
     * <p>
     * Must be a non-blank, valid Gmail address. Only addresses ending with "@gmail.com" are accepted.
     * </p>
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Only Gmail addresses are accepted")
    private String email;

    /**
     * Gets the user's name.
     *
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the user's email address.
     *
     * @return the user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
