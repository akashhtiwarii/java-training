package com.example.foreignkeyannotations.serviceImpl;

import com.example.foreignkeyannotations.dto.AddressDTO;
import com.example.foreignkeyannotations.dto.UserDTO;
import com.example.foreignkeyannotations.entity.Address;
import com.example.foreignkeyannotations.entity.Users;
import com.example.foreignkeyannotations.repository.AddressRepository;
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
    public UserDTO createUser(UserDTO userDTO) {
        Users user = mapToEntity(userDTO);
        for (Address address : user.getAddresses()) {
            address.setUser(user);
        }

        Users savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<Users> users = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for (Users user : users) {
            userDTOList.add(mapToDTO(user));
        }
        return userDTOList;
    }
}
