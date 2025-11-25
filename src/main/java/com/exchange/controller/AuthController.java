package com.exchange.controller;

import com.exchange.dto.LoginRequest;
import com.exchange.dto.RegisterRequest;
import com.exchange.model.User;
import com.exchange.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

// AuthController - handles user registration and login
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@Tag(name = "Authentication", description = "User registration, login, and profile management")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    // Register a new user
    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Create a new user account with email and password")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            // Validate input
            if (request.getName() == null || request.getLastname() == null || 
                request.getPhone() == null || request.getEmail() == null || request.getPassword() == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "All fields are required"));
            }
            
            User user = authService.register(
                request.getName(), 
                request.getLastname(), 
                request.getPhone(), 
                request.getEmail(), 
                request.getPassword()
            );
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User registered successfully");
            response.put("userId", user.getId());
            response.put("email", user.getEmail());
            response.put("address", user.getAddress());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // Login user
    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Authenticate user and receive JWT token")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            // Validate input
            if (request.getEmail() == null || request.getPassword() == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Email and password are required"));
            }
            
            String token = authService.login(request.getEmail(), request.getPassword());
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("token", token);
            response.put("email", request.getEmail());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // Delete user profile
    @DeleteMapping("/profile")
    @Operation(
        summary = "Delete user profile", 
        description = "Delete user account and auto-liquidate all crypto holdings to USD",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<?> deleteProfile(Authentication authentication) {
        try {
            String email = authentication.getName();
            String message = authService.deleteProfile(email);
            
            return ResponseEntity.ok(Map.of("message", message));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
