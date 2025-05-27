package com.example.foreignkeyannotations.convertor;

import com.example.foreignkeyannotations.dto.AddressDTO;
import com.example.foreignkeyannotations.dto.UserDTO;
import com.example.foreignkeyannotations.entity.Address;
import com.example.foreignkeyannotations.entity.Users;

import java.util.ArrayList;
import java.util.List;

public class UserConvertor {

    public static Users mapToEntity(UserDTO dto) {
        Users user = new Users();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        List<Address> addressList = new ArrayList<>();
        if (dto.getAddresses() != null) {
            for (AddressDTO adto : dto.getAddresses()) {
                Address address = new Address();
                address.setCity(adto.getCity());
                address.setState(adto.getState());
                addressList.add(address);
            }
        }

        user.setAddresses(addressList);
        return user;
    }

    public static UserDTO mapToDTO(Users user) {
        UserDTO dto = new UserDTO();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());

        List<AddressDTO> dtoList = new ArrayList<>();
        if (user.getAddresses() != null) {
            for (Address a : user.getAddresses()) {
                AddressDTO adto = new AddressDTO();
                adto.setCity(a.getCity());
                adto.setState(a.getState());
                dtoList.add(adto);
            }
        }

        dto.setAddresses(dtoList);
        return dto;
    }
}
