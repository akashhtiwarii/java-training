package com.example.hibernateworking.serviceImpl;

import com.example.hibernateworking.entity.User;
import com.example.hibernateworking.exception.ResourceAlreadyExistsException;
import com.example.hibernateworking.exception.ResourceNotFoundException;
import com.example.hibernateworking.inDTO.UserInDTO;
import com.example.hibernateworking.outDTO.UserOutDTO;
import com.example.hibernateworking.repository.UserRepository;
import com.example.hibernateworking.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.hibernateworking.convertor.UserConvertor.mapToUserOutDTO;

/**
 * Service implementation for managing users.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new user if the email is not already registered.
     *
     * @param userDto the user data transfer object
     * @return the created user as a UserOutDTO
     * @throws ResourceAlreadyExistsException if a user with the given email already exists
     */
    @Override
    public UserOutDTO createUser(UserInDTO userDto) {
        logger.info("Attempting to create user with email: {}", userDto.getEmail());

        Optional<User> existingUser = userRepository.findByEmail(userDto.getEmail());
        if (existingUser.isPresent()) {
            logger.warn("User with email {} already exists", userDto.getEmail());
            throw new ResourceAlreadyExistsException("User with email already exists");
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        User savedUser = userRepository.save(user);

        logger.info("User created with ID: {}", savedUser.getId());
        return mapToUserOutDTO(savedUser);
    }

    /**
     * Retrieves all users.
     *
     * @return a list of UserOutDTO objects
     */
    @Override
    public List<UserOutDTO> getUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll().stream()
                .map(user -> {
                    UserOutDTO dto = new UserOutDTO();
                    dto.setId(user.getId());
                    dto.setName(user.getName());
                    dto.setEmail(user.getEmail());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing user by ID.
     *
     * @param id the ID of the user to update
     * @param userDetails the updated user data
     * @return the updated user as a UserOutDTO
     * @throws ResourceNotFoundException if the user does not exist
     * @throws ResourceAlreadyExistsException if another user with the same email already exists
     */
    @Override
    public UserOutDTO updateUser(Long id, UserInDTO userDetails) {
        logger.info("Updating user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("User not found with ID: {}", id);
                    return new ResourceNotFoundException("User not found with id: " + id);
                });

        Optional<User> userByEmail = userRepository.findByEmail(userDetails.getEmail());
        if (userByEmail.isPresent() && !userByEmail.get().getId().equals(id)) {
            logger.warn("Email {} is already used by another user", userDetails.getEmail());
            throw new ResourceAlreadyExistsException("Another user with the same email already exists");
        }

        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        User updatedUser = userRepository.save(user);

        logger.info("User with ID {} successfully updated", updatedUser.getId());

        UserOutDTO dto = new UserOutDTO();
        dto.setId(updatedUser.getId());
        dto.setName(updatedUser.getName());
        dto.setEmail(updatedUser.getEmail());
        return dto;
    }

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     * @throws ResourceNotFoundException if the user does not exist
     */
    @Override
    public void deleteUser(Long id) {
        logger.info("Attempting to delete user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("User not found with ID: {}", id);
                    return new ResourceNotFoundException("User not found with id: " + id);
                });

        userRepository.delete(user);
        logger.info("User with ID {} has been deleted", id);
    }
}
