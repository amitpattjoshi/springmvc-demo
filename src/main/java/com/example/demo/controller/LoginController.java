package com.example.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Users;
import com.example.demo.service.UserService;

import static org.springframework.http.HttpStatus.*;

@RestController
public class LoginController {
    private UserService service;

    public LoginController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    @ResponseStatus(CREATED)
    public Users  registerUser(@RequestBody Users user) {
        return service.register(user);
    
    }
    @PostMapping("/login")
    public String login(@RequestBody Users user) {
        return service.verify(user);
    }

}
