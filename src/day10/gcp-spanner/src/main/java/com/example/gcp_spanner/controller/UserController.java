package com.example.gcp_spanner.controller;

import com.example.gcp_spanner.inDTO.UsersInDTO;
import com.example.gcp_spanner.outDTO.UsersOutDTO;
import com.example.gcp_spanner.serviceImpl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing user-related operations.
 * Provides endpoints for creating, retrieving, updating, and deleting users.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServiceImpl userService;

    /**
     * Creates a new user.
     *
     * @param user the user data to create
     * @return ResponseEntity containing the created user's data and HTTP status
     */
    @PostMapping
    public ResponseEntity<UsersOutDTO> create(@RequestBody UsersInDTO user) {
        logger.info("Creating new user with name: {}", user.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id the ID of the user to retrieve
     * @return ResponseEntity containing the user's data and HTTP status
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsersOutDTO> get(@PathVariable String id) {
        logger.info("Retrieving user with ID: {}", id);
        return ResponseEntity.ok(userService.getUser(id));
    }

    /**
     * Retrieves all users.
     *
     * @return ResponseEntity containing a list of all users and HTTP status
     */
    @GetMapping
    public ResponseEntity<Iterable<UsersOutDTO>> getAll() {
        logger.info("Retrieving all users");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * Updates an existing user by ID.
     *
     * @param id the ID of the user to update
     * @param user the new user data
     * @return ResponseEntity containing the updated user's data and HTTP status
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsersOutDTO> update(@PathVariable String id, @RequestBody UsersInDTO user) {
        logger.info("Updating user with ID: {}", id);
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     * @return ResponseEntity with HTTP status NO_CONTENT
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        logger.info("Deleting user with ID: {}", id);
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
