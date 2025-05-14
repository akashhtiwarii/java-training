package com.example.foreignkeyannotations.service;

import com.example.foreignkeyannotations.dto.UserDTO;
import com.example.foreignkeyannotations.entity.Address;
import com.example.foreignkeyannotations.entity.Users;

import java.util.List;

public interface UsersService {

    UserDTO createUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
}
