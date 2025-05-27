package com.example.foreignkeyannotations.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserInDTO {
    private String name;
    private String email;
    private List<AddressInDTO> addresses;
}
