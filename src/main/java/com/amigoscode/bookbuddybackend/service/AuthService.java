package com.amigoscode.bookbuddybackend.service;

import com.amigoscode.bookbuddybackend.dto.request.LoginRequest;
import com.amigoscode.bookbuddybackend.dto.request.RegisterRequest;
import com.amigoscode.bookbuddybackend.dto.response.AuthResponse;
import com.amigoscode.bookbuddybackend.model.User;
import com.amigoscode.bookbuddybackend.repository.UserRepository;
import com.amigoscode.bookbuddybackend.security.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {
        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        log.debug("📌 User registered: {}", user.getId());

        // Use email as subject for JWT
        String jwt = jwtService.generateToken(user.getId(), user.getEmail());
        return new AuthResponse(jwt);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        log.debug("📌 User login success: {}", user.getEmail());

        // Use email as subject for JWT
        String jwt = jwtService.generateToken(user.getId(), user.getEmail());
        return new AuthResponse(jwt);
    }
}