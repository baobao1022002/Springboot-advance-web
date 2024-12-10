package com.nqbao.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthControllerFromLogin {

    @GetMapping("/login2")
    public String loginPage() {
        return "login2"; //
    }
    @GetMapping("/home")
    public String home() {
        return "home";
    }
}