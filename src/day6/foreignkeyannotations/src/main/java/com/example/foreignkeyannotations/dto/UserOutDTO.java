package com.example.foreignkeyannotations.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserOutDTO {
    private Long id;
    private String name;
    private String email;
    private List<AddressOutDTO> addresses;
}
