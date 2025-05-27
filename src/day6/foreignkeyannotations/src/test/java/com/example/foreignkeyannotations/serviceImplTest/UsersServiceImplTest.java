package com.example.foreignkeyannotations.serviceImplTest;

import com.example.foreignkeyannotations.dto.AddressInDTO;
import com.example.foreignkeyannotations.dto.UserInDTO;
import com.example.foreignkeyannotations.dto.UserOutDTO;
import com.example.foreignkeyannotations.entity.Users;
import com.example.foreignkeyannotations.exceptions.BadRequestException;
import com.example.foreignkeyannotations.exceptions.ResourceNotFoundException;
import com.example.foreignkeyannotations.repository.UsersRepository;
import com.example.foreignkeyannotations.serviceImpl.UsersServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
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

        UserOutDTO result = userService.getUserById(1L);

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

        AddressInDTO address1 = new AddressInDTO();
        address1.setCity("New York");
        address1.setState("NY");

        List<AddressInDTO> addressList = new ArrayList<>();
        addressList.add(address1);

        UserInDTO dto = new UserInDTO();
        dto.setName("John Doe");
        dto.setEmail("john@example.com");
        dto.setAddresses(addressList);

        UserOutDTO saved = userService.createUser(dto);

        assertEquals("John Doe", saved.getName());
    }

    @Test
    void testSaveUser_missingName() {
        UserInDTO dto = new UserInDTO();
        dto.setName("");
        dto.setEmail("john@example.com");
        dto.setAddresses(new ArrayList<>());

        assertThrows(BadRequestException.class, () -> userService.createUser(dto));
    }

    @Test
    void testSaveUser_missingEmail() {
        UserInDTO dto = new UserInDTO();
        dto.setName("John Doe");
        dto.setEmail("");
        dto.setAddresses(new ArrayList<>());

        assertThrows(BadRequestException.class, () -> userService.createUser(dto));
    }

    @Test
    void testSaveUser_missingAddress() {
        UserInDTO dto = new UserInDTO();
        dto.setName("John Doe");
        dto.setEmail("john@example.com");
        dto.setAddresses(new ArrayList<>());

        assertThrows(BadRequestException.class, () -> userService.createUser(dto));
    }

    @Test
    void testGetAllUsers_success() {
        List<Users> users = new ArrayList<>();
        users.add(user);
        when(userRepository.findAll()).thenReturn(users);

        List<UserOutDTO> result = userService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    void testGetAllUsers_emptyList() {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(ResourceNotFoundException.class, () -> userService.getAllUsers());
    }
}
