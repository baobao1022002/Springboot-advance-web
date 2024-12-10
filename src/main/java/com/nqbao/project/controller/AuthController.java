package com.nqbao.project.controller;

import com.nqbao.project.model.LoginRequest;
import com.nqbao.project.model.LoginResponse;
import com.nqbao.project.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest info) {
        var cred = new UsernamePasswordAuthenticationToken(info.getUsername(), info.getPassword());
        authenticationManager.authenticate(cred);

        // continue if passed with no exceptions thrown
        var user = userService.loadUserByUsername(info.getUsername());
        String token = tokenService.generateToken(user);
            LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setUsername(info.getUsername());
        response.setType("Bearer");
        return response;
    }

}