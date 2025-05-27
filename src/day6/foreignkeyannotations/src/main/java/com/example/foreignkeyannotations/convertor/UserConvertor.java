package com.example.foreignkeyannotations.convertor;

import com.example.foreignkeyannotations.dto.AddressInDTO;
import com.example.foreignkeyannotations.dto.AddressOutDTO;
import com.example.foreignkeyannotations.dto.UserInDTO;
import com.example.foreignkeyannotations.dto.UserOutDTO;
import com.example.foreignkeyannotations.entity.Address;
import com.example.foreignkeyannotations.entity.Users;

import java.util.ArrayList;
import java.util.List;

public class UserConvertor {

    public static Users mapToEntity(UserInDTO dto) {
        Users user = new Users();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        List<Address> addressList = new ArrayList<>();
        if (dto.getAddresses() != null) {
            for (AddressInDTO adto : dto.getAddresses()) {
                Address address = new Address();
                address.setCity(adto.getCity());
                address.setState(adto.getState());
                addressList.add(address);
            }
        }

        user.setAddresses(addressList);
        return user;
    }

    public static UserOutDTO mapToDTO(Users user) {
        UserOutDTO dto = new UserOutDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());

        List<AddressOutDTO> dtoList = new ArrayList<>();
        if (user.getAddresses() != null) {
            for (Address a : user.getAddresses()) {
                AddressOutDTO adto = new AddressOutDTO();
                adto.setId(a.getId());
                adto.setCity(a.getCity());
                adto.setState(a.getState());
                dtoList.add(adto);
            }
        }

        dto.setAddresses(dtoList);
        return dto;
    }
}
