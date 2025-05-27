package com.example.gcp_spanner.entity;

import com.google.cloud.spring.data.spanner.core.mapping.PrimaryKey;
import com.google.cloud.spring.data.spanner.core.mapping.Table;

/**
 * Represents a user entity in the "users" table of the Cloud Spanner database.
 * This class is used with Spring Data Cloud Spanner for object-relational mapping.
 */
@Table(name = "users")
public class Users {

    /**
     * The unique identifier for the user.
     */
    @PrimaryKey
    private String id;

    /**
     * The name of the user.
     */
    private String name;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * Gets the user's unique identifier.
     *
     * @return the ID of the user
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the user's unique identifier.
     *
     * @param id the ID to set for the user
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
     * @param name the name to set for the user
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
     * @param email the email to set for the user
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
