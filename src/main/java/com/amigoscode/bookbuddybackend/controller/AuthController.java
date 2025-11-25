////////package com.amigoscode.bookbuddybackend.controller;
////////
////////import com.amigoscode.bookbuddybackend.dto.request.LoginRequest;
////////import com.amigoscode.bookbuddybackend.dto.request.RegisterRequest;
////////import com.amigoscode.bookbuddybackend.dto.response.AuthResponse;
////////import com.amigoscode.bookbuddybackend.service.AuthService;
////////import org.springframework.http.ResponseEntity;
////////import org.springframework.web.bind.annotation.PostMapping;
////////import org.springframework.web.bind.annotation.RequestBody;
////////import org.springframework.web.bind.annotation.RequestMapping;
////////import org.springframework.web.bind.annotation.RestController;
////////
////////@RestController
////////@RequestMapping("/api/auth")
////////public class AuthController {
////////
////////    private final AuthService authService;
////////
////////    public AuthController(AuthService authService) {
////////        this.authService = authService;
////////    }
////////
////////    @PostMapping("/register")
////////    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
////////        return ResponseEntity.ok(authService.register(request));
////////    }
////////
////////    @PostMapping("/login")
////////    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
////////        return ResponseEntity.ok(authService.login(request));
////////    }
////////}
//////
//////
//////package com.amigoscode.bookbuddybackend.controller;
//////
//////import com.amigoscode.bookbuddybackend.dto.request.LoginRequest;
//////import com.amigoscode.bookbuddybackend.dto.request.RegisterRequest;
//////import com.amigoscode.bookbuddybackend.dto.response.AuthResponse;
//////import com.amigoscode.bookbuddybackend.service.AuthService;
//////import lombok.extern.slf4j.Slf4j;
//////import org.springframework.http.ResponseEntity;
//////import org.springframework.web.bind.annotation.*;
//////
//////@Slf4j
//////@RestController
//////@RequestMapping("/api/auth")
//////public class AuthController {
//////
//////    private final AuthService authService;
//////
//////    public AuthController(AuthService authService) {
//////        this.authService = authService;
//////    }
//////
//////    @PostMapping("/register")
//////    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
//////        log.debug("📥 /register called with email: {}", request.getEmail());
//////        try {
//////            AuthResponse response = authService.register(request);
//////            log.debug("✅ /register success, JWT length: {}");
//////            return ResponseEntity.ok(response);
//////        } catch (Exception e) {
//////            log.error("❌ /register failed for email {}: {}", request.getEmail(), e.getMessage(), e);
//////            return ResponseEntity.badRequest().build();
//////        }
//////    }
//////
//////    @PostMapping("/login")
//////    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
//////        log.debug("📥 /login called with email: {}", request.getEmail());
//////        try {
//////            AuthResponse response = authService.login(request);
//////            log.debug("✅ /login success, JWT length: {}");
//////            return ResponseEntity.ok(response);
//////        } catch (Exception e) {
//////            log.error("❌ /login failed for email {}: {}", request.getEmail(), e.getMessage(), e);
//////            return ResponseEntity.status(401).build();
//////        }
//////    }
//////}
////
////
////package com.amigoscode.bookbuddybackend.controller;
////
////import com.amigoscode.bookbuddybackend.dto.request.LoginRequest;
////import com.amigoscode.bookbuddybackend.dto.request.RegisterRequest;
////import com.amigoscode.bookbuddybackend.dto.response.AuthResponse;
////import com.amigoscode.bookbuddybackend.service.AuthService;
////import lombok.extern.slf4j.Slf4j;
////import org.springframework.http.ResponseEntity;
////import org.springframework.web.bind.annotation.*;
////
////@Slf4j
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
////        log.debug("📥 /register called with email: {}", request.getEmail());
////        try {
////            AuthResponse response = authService.register(request);
////            log.debug("✅ /register success, JWT length: {}", response.getJwt().length());
////            return ResponseEntity.ok(response);
////        } catch (Exception e) {
////            log.error("❌ /register failed for email {}: {}", request.getEmail(), e.getMessage(), e);
////            return ResponseEntity.badRequest().build();
////        }
////    }
////
////    @PostMapping("/login")
////    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
////        log.debug("📥 /login called with email: {}", request.getEmail());
////        try {
////            AuthResponse response = authService.login(request);
////            log.debug("✅ /login success, JWT length: {}", response.getJwt().length());
////            return ResponseEntity.ok(response);
////        } catch (Exception e) {
////            log.error("❌ /login failed for email {}: {}", request.getEmail(), e.getMessage(), e);
////            return ResponseEntity.status(401).build();
////        }
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
//    // ==========================
//    // Register Endpoint
//    // ==========================
//    @PostMapping("/register")
//    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
//        log.debug("📥 /register called with email: {}", request.getEmail());
//
//        // Let exceptions propagate to GlobalExceptionHandler
//        AuthResponse response = authService.register(request);
//
//        log.debug("✅ /register success, JWT length: {}", response.getJwt().length());
//        return ResponseEntity.ok(response);
//    }
//
//    // ==========================
//    // Login Endpoint
//    // ==========================
//    @PostMapping("/login")
//    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
//        log.debug("📥 /login called with email: {}", request.getEmail());
//
//        // Let exceptions propagate to GlobalExceptionHandler
//        AuthResponse response = authService.login(request);
//
//        log.debug("✅ /login success, JWT length: {}", response.getJwt().length());
//        return ResponseEntity.ok(response);
//    }
//}

package com.amigoscode.bookbuddybackend.controller;

import com.amigoscode.bookbuddybackend.dto.request.LoginRequest;
import com.amigoscode.bookbuddybackend.dto.request.RegisterRequest;
import com.amigoscode.bookbuddybackend.dto.response.AuthResponse;
import com.amigoscode.bookbuddybackend.model.User;
import com.amigoscode.bookbuddybackend.repository.UserRepository;
import com.amigoscode.bookbuddybackend.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    private UserRepository userRepository;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ==========================
    // Register Endpoint
    // ==========================
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        log.debug("/register called with email: {}", request.getEmail());
        AuthResponse response = authService.register(request);
        log.debug("Registration successful for {}", request.getEmail());
        return ResponseEntity.ok(response);
    }

    // ==========================
    // Login Endpoint
    // ==========================
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        log.debug("/login called with email: {}", request.getEmail());
        AuthResponse response = authService.login(request);
        log.debug("Login successful for {}", request.getEmail());
        return ResponseEntity.ok(response);
    }

    // ==========================
    // GET /api/auth/me – Current User Profile
    // ==========================
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated() || authentication.getName() == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
        }

        String userId = authentication.getName(); // This is the JWT subject (sub) – your User ID

        log.debug("Fetching profile for userId: {}", userId);

        User user = userRepository.findById(userId)
                .or(() -> userRepository.findByEmail(userId))
                .orElse(null);

        if (user == null) {
            log.warn("User not found in DB for userId: {}", userId);
            return ResponseEntity.ok(Map.of(
                    "userId", userId,
                    "email", "unknown@example.com",
                    "name", "Guest User",
                    "picture", null,
                    "joinedAt", null,
                    "totalBooks", 0,
                    "message", "User profile not fully synced yet"
            ));
        }

        String displayName = user.getFullName();
        if (displayName == null || displayName.isBlank()) {
            displayName = user.getEmail().split("@")[0];
        }

        int totalBooks = user.getUserBooks() != null ? user.getUserBooks().size() : 0;

        var profile = Map.of(
                "userId", user.getId(),
                "email", user.getEmail(),
                "name", displayName,

                "totalBooks", totalBooks,
                "roles", authentication.getAuthorities()
        );

        log.debug("Profile returned for user: {} ({} books)", user.getEmail(), totalBooks);
        return ResponseEntity.ok(profile);
    }
}