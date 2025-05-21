package com.example.foreignkeyannotations.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private String name;
    private String email;
    private List<AddressDTO> addresses;

}
