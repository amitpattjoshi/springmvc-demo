package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.service.MyUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Autowired
    private MyUserDetailService myUserDetailService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz -> authz
            .requestMatchers("/register", "/login").permitAll()
            .anyRequest().authenticated()
        );
         http.httpBasic(Customizer.withDefaults());
         http.csrf(customizer -> customizer.disable());
        //  http.formLogin(Customizer.withDefaults());
         http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

         return http.build();        
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(myUserDetailService);
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return authProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
