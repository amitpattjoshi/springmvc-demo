package com.example.demo.controller;

import com.example.demo.entity.Users;
import com.example.demo.model.Student;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {
    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello, Spring Boot!";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/students")
    public Users createStudent(@RequestBody Student student) {
        Users user = new Users();
        user.setUsername(student.username());
        user.setPassword(student.password());
        return userService.register(user);
    }
}
