package com.example.hibernateworking.outDTO;

/**
 * Data Transfer Object (DTO) for outputting user information.
 * <p>
 * This class is used to send user details to the client without exposing the full User entity.
 * </p>
 */
public class UserOutDTO {

    /**
     * The unique identifier of the user.
     */
    private Long id;

    /**
     * The name of the user.
     */
    private String name;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * Default no-args constructor.
     */
    public UserOutDTO() {
    }

    /**
     * Parameterized constructor for creating a UserOutDTO with all fields.
     *
     * @param id    the user ID
     * @param name  the user's name
     * @param email the user's email
     */
    public UserOutDTO(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Gets the user ID.
     *
     * @return the user ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the user ID.
     *
     * @param id the user ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

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
