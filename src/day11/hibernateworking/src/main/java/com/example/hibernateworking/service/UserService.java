package com.example.hibernateworking.service;

import com.example.hibernateworking.inDTO.UserInDTO;
import com.example.hibernateworking.outDTO.UserOutDTO;

import java.util.List;

/**
 * Service interface for managing users.
 */
public interface UserService {

    /**
     * Creates a new user.
     *
     * @param user the user input data transfer object
     * @return the created user represented as a {@link UserOutDTO}
     * @throws com.example.hibernateworking.exception.ResourceAlreadyExistsException
     *         if a user with the same email already exists
     */
    UserOutDTO createUser(UserInDTO user);

    /**
     * Retrieves all users from the system.
     *
     * @return a list of {@link UserOutDTO} representing all users
     */
    List<UserOutDTO> getUsers();

    /**
     * Updates an existing user's details.
     *
     * @param id the ID of the user to update
     * @param userDetails the updated user details as {@link UserInDTO}
     * @return the updated user represented as a {@link UserOutDTO}
     * @throws com.example.hibernateworking.exception.ResourceNotFoundException
     *         if the user with the given ID is not found
     * @throws com.example.hibernateworking.exception.ResourceAlreadyExistsException
     *         if another user with the given email already exists
     */
    UserOutDTO updateUser(Long id, UserInDTO userDetails);

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     * @throws com.example.hibernateworking.exception.ResourceNotFoundException
     *         if the user with the given ID does not exist
     */
    void deleteUser(Long id);
}
