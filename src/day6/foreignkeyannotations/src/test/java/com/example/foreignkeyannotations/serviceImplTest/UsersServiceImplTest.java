package com.example.foreignkeyannotations.serviceImplTest;

import com.example.foreignkeyannotations.dto.UserDTO;
import com.example.foreignkeyannotations.entity.Users;
import com.example.foreignkeyannotations.exceptions.ResourceNotFoundException;
import com.example.foreignkeyannotations.repository.UsersRepository;
import com.example.foreignkeyannotations.serviceImpl.UsersServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsersServiceImplTest {

    @Mock
    private UsersRepository userRepository;

    @InjectMocks
    private UsersServiceImpl userService;

    private Users user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new Users();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@example.com");
    }

    @Test
    void testGetUserById_success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDTO result = userService.getUserById(1L);

        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
    }

    @Test
    void testGetUserById_notFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void testSaveUser() {
        when(userRepository.save(any(Users.class))).thenReturn(user);

        UserDTO dto = new UserDTO();
        dto.setName("John Doe");
        dto.setEmail("john@example.com");

        UserDTO saved = userService.createUser(dto);
        assertEquals("John Doe", saved.getName());
    }
}
