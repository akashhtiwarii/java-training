package com.example.foreignkeyannotations.controller;

import com.example.foreignkeyannotations.dto.UserDTO;
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
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
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
