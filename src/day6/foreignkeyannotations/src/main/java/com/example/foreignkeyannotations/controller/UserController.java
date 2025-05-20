package com.example.foreignkeyannotations.controller;

import com.example.foreignkeyannotations.dto.UserInDTO;
import com.example.foreignkeyannotations.dto.UserOutDTO;
import com.example.foreignkeyannotations.exceptions.BadRequestException;
import com.example.foreignkeyannotations.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UsersService userService;

    @PostMapping
    public UserOutDTO createUser(@RequestBody UserInDTO userInDTO) {
        return userService.createUser(userInDTO);
    }

    @GetMapping
    public List<UserOutDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserOutDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/validate")
    public void triggerBadRequest() {
        throw new BadRequestException("Invalid input received");
    }

    @GetMapping("/crash")
    public void crash() {
        throw new RuntimeException("Something broke");
    }
}
