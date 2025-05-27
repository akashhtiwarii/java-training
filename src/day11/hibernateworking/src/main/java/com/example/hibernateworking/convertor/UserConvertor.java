package com.example.hibernateworking.convertor;

import com.example.hibernateworking.entity.User;
import com.example.hibernateworking.outDTO.UserOutDTO;

public class UserConvertor {
    public static UserOutDTO mapToUserOutDTO(User user) {
        return new UserOutDTO(user.getId(), user.getName(), user.getEmail());
    }
}
