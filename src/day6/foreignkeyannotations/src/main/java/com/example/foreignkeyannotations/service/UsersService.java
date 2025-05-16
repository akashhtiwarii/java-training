package com.example.foreignkeyannotations.service;

import com.example.foreignkeyannotations.dto.UserInDTO;
import com.example.foreignkeyannotations.dto.UserOutDTO;

import java.util.List;

public interface UsersService {

    UserOutDTO createUser(UserInDTO userInDTO);
    List<UserOutDTO> getAllUsers();
    UserOutDTO getUserById(Long id);
}
