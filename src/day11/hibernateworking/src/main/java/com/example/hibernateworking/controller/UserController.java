package com.example.hibernateworking.controller;

import com.example.hibernateworking.entity.User;
import com.example.hibernateworking.inDTO.UserInDTO;
import com.example.hibernateworking.outDTO.UserOutDTO;
import com.example.hibernateworking.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * Creates a new user.
     *
     * @param userDto the user data transfer object
     * @return the created user as a UserOutDTO
     */
    @PostMapping
    public UserOutDTO createUser(@RequestBody UserInDTO userDto) {
        logger.info("Creating new user with name: {}", userDto.getName());
        return userService.createUser(userDto);
    }

    /**
     * Retrieves all users.
     *
     * @return a list of UserOutDTO objects
     */
    @GetMapping
    public List<UserOutDTO> getUsers() {
        logger.info("Fetching all users");
        return userService.getUsers();
    }

    /**
     * Updates an existing user by ID.
     *
     * @param id the ID of the user to update
     * @param userDetails the new user details
     * @return the updated user as a UserOutDTO
     */
    @PutMapping("/{id}")
    public UserOutDTO updateUser(@PathVariable Long id, @RequestBody UserInDTO userDetails) {
        logger.info("Updating user with ID: {}", id);
        return userService.updateUser(id, userDetails);
    }

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        logger.info("Deleting user with ID: {}", id);
        userService.deleteUser(id);
    }
}
