package com.example.usercrud.serviceImpl;

import com.example.usercrud.convertor.UserConvertor;
import com.example.usercrud.dto.UserDTO;
import com.example.usercrud.entity.Users;
import com.example.usercrud.repository.UserRepository;
import com.example.usercrud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.usercrud.convertor.UserConvertor.convertToDTO;
import static com.example.usercrud.convertor.UserConvertor.convertToEntity;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        Users users = convertToEntity(userDTO);
        Users saved = userRepository.save(users);
        return convertToDTO(saved);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserConvertor::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        Users users = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDTO(users);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO dto) {
        Users users = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        users.setName(dto.getName());
        users.setEmail(dto.getEmail());
        return convertToDTO(userRepository.save(users));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
