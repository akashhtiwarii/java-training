package com.example.usercrud.convertor;

import com.example.usercrud.dto.UserDTO;
import com.example.usercrud.entity.Users;

public class UserConvertor {

    public static UserDTO convertToDTO(Users users) {
        UserDTO dto = new UserDTO();
        dto.setId(users.getId());
        dto.setName(users.getName());
        dto.setEmail(users.getEmail());
        return dto;
    }

    public static Users convertToEntity(UserDTO dto) {
        Users users = new Users();
        users.setName(dto.getName());
        users.setEmail(dto.getEmail());
        return users;
    }
}
