package com.example.usercrud.service;

import com.example.usercrud.dto.UserDTO;

import java.util.List;

public interface UserService {
    public UserDTO createUser(UserDTO userDTO);
    public List<UserDTO> getAllUsers();
    public UserDTO getUserById(Long id);
    public UserDTO updateUser(Long id, UserDTO dto);
    public void deleteUser(Long id);
}
