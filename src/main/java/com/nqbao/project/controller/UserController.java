package com.nqbao.project.controller;

import com.nqbao.project.model.User;
import com.nqbao.project.model.UserDTO;
import com.nqbao.project.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "Keycloak")

public class UserController extends GenericController<User, Integer, UserDTO> {

    public UserController(UserService service) {
        super(service);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO user) throws NoSuchFieldException, IllegalAccessException {
        UserDTO registerUser = super.create(user);
        return new ResponseEntity<>((registerUser), HttpStatus.CREATED);
    }

}
