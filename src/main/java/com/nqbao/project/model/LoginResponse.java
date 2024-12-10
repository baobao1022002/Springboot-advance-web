package com.nqbao.project.model;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String type;
    private String username;
}
