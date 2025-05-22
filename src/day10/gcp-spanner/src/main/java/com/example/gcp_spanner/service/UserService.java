package com.example.gcp_spanner.service;

import com.example.gcp_spanner.inDTO.UsersInDTO;
import com.example.gcp_spanner.outDTO.UsersOutDTO;

/**
 * Service interface for managing user operations.
 * Defines the business logic contract for creating, retrieving, updating, and deleting users.
 */
public interface UserService {

    /**
     * Creates a new user based on the provided input DTO.
     *
     * @param usersInDTO the input data for creating a user
     * @return a DTO representing the created user
     */
    UsersOutDTO createUser(UsersInDTO usersInDTO);

    /**
     * Updates an existing user identified by the given ID with the provided data.
     *
     * @param id the ID of the user to update
     * @param updatedUser the updated user data
     * @return a DTO representing the updated user
     */
    UsersOutDTO updateUser(String id, UsersInDTO updatedUser);

    /**
     * Deletes the user with the specified ID.
     *
     * @param id the ID of the user to delete
     */
    void deleteUser(String id);

    /**
     * Retrieves all users.
     *
     * @return an iterable collection of user DTOs
     */
    Iterable<UsersOutDTO> getAllUsers();

    /**
     * Retrieves a user by their unique ID.
     *
     * @param id the ID of the user to retrieve
     * @return a DTO representing the user
     */
    UsersOutDTO getUser(String id);
}
