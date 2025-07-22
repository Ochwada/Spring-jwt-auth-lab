package com.ochwada.jwt_auth_lab.controller;


import com.ochwada.jwt_auth_lab.model.Role;
import com.ochwada.jwt_auth_lab.model.User;
import com.ochwada.jwt_auth_lab.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * *******************************************************
 * Package: com.ochwada.jwt_auth_lab.controller
 * File: AuthController.java
 * Author: Ochwada
 * Date: Monday, 21.Jul.2025, 5:55 PM
 * Description:
 * Objective:
 * *******************************************************
 */

@RestController
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/login") // or @PostMapping for real apps
    public String login() {

        User demoUser = new User(
                "john", // username
                "password", // plain-text password (ok for test only)
                List.of(Role.USER));  // roles from your enum

        // Generate JWT
        String token = jwtUtil.generateToken(demoUser.getName(), demoUser.getRoles());

        return token;

    }



}
