package com.exchange.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User login request")
public class LoginRequest {
    
    @Schema(description = "User's email address", example = "john@example.com", required = true)
    private String email;
    
    @Schema(description = "User's password", example = "password123", required = true)
    private String password;
    
    // Constructors
    public LoginRequest() {}
    
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    // Getters and Setters
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}
