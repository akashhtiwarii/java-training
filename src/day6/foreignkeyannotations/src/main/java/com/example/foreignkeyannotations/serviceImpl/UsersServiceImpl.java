package com.example.foreignkeyannotations.serviceImpl;

import com.example.foreignkeyannotations.dto.UserInDTO;
import com.example.foreignkeyannotations.dto.UserOutDTO;
import com.example.foreignkeyannotations.entity.Address;
import com.example.foreignkeyannotations.entity.Users;
import com.example.foreignkeyannotations.exceptions.BadRequestException;
import com.example.foreignkeyannotations.exceptions.ResourceNotFoundException;
import com.example.foreignkeyannotations.repository.UsersRepository;
import com.example.foreignkeyannotations.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.foreignkeyannotations.convertor.UserConvertor.mapToDTO;
import static com.example.foreignkeyannotations.convertor.UserConvertor.mapToEntity;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository userRepository;

    @Override
    @Transactional
    public UserOutDTO createUser(UserInDTO userInDTO) {
        if(userInDTO.getName().isEmpty() || userInDTO.getEmail().isEmpty() || userInDTO.getAddresses().isEmpty()) {
            throw new BadRequestException("Invalid Request");
        }

        if(userRepository.existsByEmail(userInDTO.getEmail())) {
            throw new BadRequestException("Email already exists: " + userInDTO.getEmail());
        }

        Users user = mapToEntity(userInDTO);
        for (Address address : user.getAddresses()) {
            address.setUser(user);
        }

        Users savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }


    @Override
    public List<UserOutDTO> getAllUsers() {
        List<Users> users = userRepository.findAll();
        if(users.isEmpty()) {
            throw new ResourceNotFoundException("No Users Found");
        }
        List<UserOutDTO> userInDTOList = new ArrayList<>();
        for (Users user : users) {
            userInDTOList.add(mapToDTO(user));
        }
        return userInDTOList;
    }

    @Override
    public UserOutDTO getUserById(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        return mapToDTO(user);
    }

}
