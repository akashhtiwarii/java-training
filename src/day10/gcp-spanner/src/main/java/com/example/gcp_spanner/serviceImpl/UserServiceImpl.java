package com.example.gcp_spanner.serviceImpl;

import com.example.gcp_spanner.exception.ResourceAlreadyExistsException;
import com.example.gcp_spanner.exception.ResourceInvalidException;
import com.example.gcp_spanner.exception.ResourceNotFoundException;
import com.example.gcp_spanner.inDTO.UsersInDTO;
import com.example.gcp_spanner.entity.Users;
import com.example.gcp_spanner.outDTO.UsersOutDTO;
import com.example.gcp_spanner.repository.UserRepository;
import com.example.gcp_spanner.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Implementation of the {@link UserService} interface that handles business logic
 * related to user creation, retrieval, update, and deletion.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new user if the input data is valid and the ID doesn't already exist.
     *
     * @param usersInDTO the user data to create
     * @return the created user's data
     */
    @Override
    public UsersOutDTO createUser(UsersInDTO usersInDTO) {
        logger.info("Creating user with ID: {}", usersInDTO.getId());

        if (usersInDTO.getName() == null || usersInDTO.getEmail() == null) {
            logger.error("User name or email is null");
            throw new ResourceInvalidException("User name and email must not be null");
        }

        if (usersInDTO.getId() == null || usersInDTO.getId().isEmpty()) {
            usersInDTO.setId(UUID.randomUUID().toString());
            logger.debug("Generated new UUID for user: {}", usersInDTO.getId());
        } else if (userRepository.existsById(usersInDTO.getId())) {
            logger.error("User with ID {} already exists", usersInDTO.getId());
            throw new ResourceAlreadyExistsException("User", "ID", usersInDTO.getId());
        }

        Users user = new Users();
        user.setId(usersInDTO.getId());
        user.setName(usersInDTO.getName());
        user.setEmail(usersInDTO.getEmail());

        Users savedUser = userRepository.save(user);
        logger.info("User created successfully with ID: {}", savedUser.getId());

        UsersOutDTO usersOutDTO = new UsersOutDTO();
        usersOutDTO.setName(savedUser.getName());
        usersOutDTO.setEmail(savedUser.getEmail());
        return usersOutDTO;
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id the ID of the user to retrieve
     * @return the user's data
     */
    @Override
    public UsersOutDTO getUser(String id) {
        logger.info("Fetching user with ID: {}", id);

        Users savedUser = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("User not found with ID: {}", id);
                    return new ResourceNotFoundException("User", "ID", id);
                });

        UsersOutDTO usersOutDTO = new UsersOutDTO();
        usersOutDTO.setName(savedUser.getName());
        usersOutDTO.setEmail(savedUser.getEmail());
        logger.info("User fetched successfully: {}", id);
        return usersOutDTO;
    }

    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    @Override
    public Iterable<UsersOutDTO> getAllUsers() {
        logger.info("Fetching all users");

        var users = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(user -> {
                    UsersOutDTO dto = new UsersOutDTO();
                    dto.setName(user.getName());
                    dto.setEmail(user.getEmail());
                    return dto;
                })
                .collect(Collectors.toList());

        if (users.isEmpty()) {
            logger.warn("No users found in the database");
            throw new ResourceNotFoundException("Users", "data", "none");
        }

        logger.info("Users fetched successfully, count: {}", users.size());
        return users;
    }

    /**
     * Updates an existing user's details by ID.
     *
     * @param id the ID of the user to update
     * @param updatedUser the updated user data
     * @return the updated user's data
     */
    @Override
    public UsersOutDTO updateUser(String id, UsersInDTO updatedUser) {
        logger.info("Updating user with ID: {}", id);

        if (updatedUser.getName() == null || updatedUser.getEmail() == null) {
            logger.error("Invalid input data: name or email is null");
            throw new ResourceInvalidException("User name and email must not be null");
        }

        Users user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("User not found with ID: {}", id);
                    return new ResourceNotFoundException("User", "ID", id);
                });

        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());

        Users savedUser = userRepository.save(user);
        logger.info("User updated successfully: {}", id);

        UsersOutDTO dto = new UsersOutDTO();
        dto.setName(savedUser.getName());
        dto.setEmail(savedUser.getEmail());
        return dto;
    }

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     */
    @Override
    public void deleteUser(String id) {
        logger.info("Deleting user with ID: {}", id);

        if (!userRepository.existsById(id)) {
            logger.error("User not found for deletion with ID: {}", id);
            throw new ResourceNotFoundException("User", "ID", id);
        }

        userRepository.deleteById(id);
        logger.info("User deleted successfully: {}", id);
    }
}
