package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Import;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.example.demo.service.UserService;
import com.example.demo.config.TestSecurityConfig;

@WebMvcTest(HelloController.class)
@AutoConfigureMockMvc(addFilters = true)
@Import(TestSecurityConfig.class)
class HelloControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UserService userService;

    @Test
    void helloEndpointReturnsExpectedString() throws Exception {
        mockMvc.perform(get("/hello").with(httpBasic("amit", "amit123")))
            .andExpect(status().isOk())
            .andExpect(content().string("Hello, Spring Boot!"));
    }
}
