package com.example.gcp_spanner.inDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Data Transfer Object for incoming user data used in create and update operations.
 * Includes validation constraints to ensure data integrity.
 */
public class UsersInDTO {

    /**
     * The unique identifier for the user.
     * Must be a non-blank string with 2–30 alphabetic characters, spaces, hyphens, or apostrophes.
     */
    @NotBlank(message = "First name is required")
    @Pattern(regexp = "^[A-Za-z\\s-']{2,30}$", message = "First name should be valid")
    private String id;

    /**
     * The name of the user.
     * Must be a non-blank string with 2–30 alphabetic characters, spaces, hyphens, or apostrophes.
     */
    @NotBlank(message = "First name is required")
    @Pattern(regexp = "^[A-Za-z\\s-']{2,30}$", message = "First name should be valid")
    private String name;

    /**
     * The email address of the user.
     * Must be a non-blank, valid Gmail address.
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Only Gmail addresses are accepted")
    private String email;

    /**
     * Gets the user's ID.
     *
     * @return the ID of the user
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the user's ID.
     *
     * @param id the ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the user's name.
     *
     * @return the name of the user
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
     * @return the email of the user
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
