package com.example.demo.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Users;
import com.example.demo.repo.UsersRepository;

@Service
public class UserService {
    private UsersRepository usersRepository;
    private AuthenticationManager authenticationManager;
    
    public UserService(UsersRepository usersRepository, AuthenticationManager authenticationManager){
        this.usersRepository = usersRepository;
        this.authenticationManager = authenticationManager;
    }
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users register(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }
    public String verify(Users user) {
        Authentication  authentication =  authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        return authentication.isAuthenticated() ? "success" : "failure";
    }
    
}
