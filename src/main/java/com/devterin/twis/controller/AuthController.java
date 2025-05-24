package com.devterin.twis.controller;

import com.devterin.twis.model.User;
import com.devterin.twis.repository.UserRepository;
import com.devterin.twis.request.LoginRequest;
import com.devterin.twis.response.AuthResponse;
import com.devterin.twis.service.AuthService;
import com.devterin.twis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public AuthResponse registerUser(@RequestBody User user) {
        return authService.registerUser(user);
    }


    @PostMapping("/signin")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }


}
