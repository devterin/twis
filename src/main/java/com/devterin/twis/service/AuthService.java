package com.devterin.twis.service;

import com.devterin.twis.model.User;
import com.devterin.twis.repository.UserRepository;
import com.devterin.twis.request.LoginRequest;
import com.devterin.twis.response.ApiResponse;
import com.devterin.twis.response.AuthResponse;
import com.devterin.twis.security.CustomUserDetailsService;
import com.devterin.twis.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    public AuthResponse registerUser(User user) {
        User isExist = userRepository.findByEmail(user.getEmail());
        if (isExist != null) {
            throw new RuntimeException("Email existed");
        }
        User newUser = User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();
        User saved = userRepository.save(newUser);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(saved.getEmail(), saved.getPassword());
        String token = JwtUtils.generateToken(authentication);

        return AuthResponse.builder()
                .token(token)
                .message("Register success")
                .build();
    }
    public AuthResponse login(LoginRequest request) {

        Authentication authentication = authenticate(request.getEmail(), request.getPassword());
        String token = JwtUtils.generateToken(authentication);

        return AuthResponse.builder()
                .token(token)
                .message("Login successfully")
                .build();
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Password not matches");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }


}
