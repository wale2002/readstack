////package com.amigoscode.bookbuddybackend.controller;
////
////import com.amigoscode.bookbuddybackend.dto.request.LoginRequest;
////import com.amigoscode.bookbuddybackend.dto.request.RegisterRequest;
////import com.amigoscode.bookbuddybackend.dto.response.AuthResponse;
////import com.amigoscode.bookbuddybackend.service.AuthService;
////import org.springframework.http.ResponseEntity;
////import org.springframework.web.bind.annotation.PostMapping;
////import org.springframework.web.bind.annotation.RequestBody;
////import org.springframework.web.bind.annotation.RequestMapping;
////import org.springframework.web.bind.annotation.RestController;
////
////@RestController
////@RequestMapping("/api/auth")
////public class AuthController {
////
////    private final AuthService authService;
////
////    public AuthController(AuthService authService) {
////        this.authService = authService;
////    }
////
////    @PostMapping("/register")
////    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
////        return ResponseEntity.ok(authService.register(request));
////    }
////
////    @PostMapping("/login")
////    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
////        return ResponseEntity.ok(authService.login(request));
////    }
////}
//
//
//package com.amigoscode.bookbuddybackend.controller;
//
//import com.amigoscode.bookbuddybackend.dto.request.LoginRequest;
//import com.amigoscode.bookbuddybackend.dto.request.RegisterRequest;
//import com.amigoscode.bookbuddybackend.dto.response.AuthResponse;
//import com.amigoscode.bookbuddybackend.service.AuthService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@Slf4j
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    private final AuthService authService;
//
//    public AuthController(AuthService authService) {
//        this.authService = authService;
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
//        log.debug("📥 /register called with email: {}", request.getEmail());
//        try {
//            AuthResponse response = authService.register(request);
//            log.debug("✅ /register success, JWT length: {}");
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            log.error("❌ /register failed for email {}: {}", request.getEmail(), e.getMessage(), e);
//            return ResponseEntity.badRequest().build();
//        }
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
//        log.debug("📥 /login called with email: {}", request.getEmail());
//        try {
//            AuthResponse response = authService.login(request);
//            log.debug("✅ /login success, JWT length: {}");
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            log.error("❌ /login failed for email {}: {}", request.getEmail(), e.getMessage(), e);
//            return ResponseEntity.status(401).build();
//        }
//    }
//}


package com.amigoscode.bookbuddybackend.controller;

import com.amigoscode.bookbuddybackend.dto.request.LoginRequest;
import com.amigoscode.bookbuddybackend.dto.request.RegisterRequest;
import com.amigoscode.bookbuddybackend.dto.response.AuthResponse;
import com.amigoscode.bookbuddybackend.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        log.debug("📥 /register called with email: {}", request.getEmail());
        try {
            AuthResponse response = authService.register(request);
            log.debug("✅ /register success, JWT length: {}", response.getJwt().length());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("❌ /register failed for email {}: {}", request.getEmail(), e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        log.debug("📥 /login called with email: {}", request.getEmail());
        try {
            AuthResponse response = authService.login(request);
            log.debug("✅ /login success, JWT length: {}", response.getJwt().length());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("❌ /login failed for email {}: {}", request.getEmail(), e.getMessage(), e);
            return ResponseEntity.status(401).build();
        }
    }
}
