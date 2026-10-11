package com.example.demo.controller;


import com.example.demo.entity.Users;
import com.example.demo.repo.UsersRepository;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Testcontainers
public class HelloControllerIT {
    static ObjectMapper objectMapper;
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserService userService;
    @Autowired
    UsersRepository usersRepository;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        postgreSQLContainer.start();
    }


    @Test
    void registerUserWithNoAuthNRequired() throws Exception {
        Users user = new Users();
        user.setUsername("amit");
        user.setPassword("amit123");

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cresteJsonString(user))
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        usersRepository.deleteAll();
    }

    @Test
    void callHelloWithBasicAuthN() throws Exception {
        Users user = new Users();
        user.setUsername("amit");
        user.setPassword("amit123");

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cresteJsonString(user)));

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/hello")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(httpBasic("amit", "amit123"))
        ).andExpect(MockMvcResultMatchers.status().isOk());
        usersRepository.deleteAll();

    }

    private String cresteJsonString(Users user) throws JsonProcessingException {
        return objectMapper.writeValueAsString(user);
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }
    @AfterAll
    public static void tearDown() throws InterruptedException {
        Thread.sleep(10000);
        postgreSQLContainer.stop();
    }
}
