package com.exchange.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User registration request")
public class RegisterRequest {
    
    @Schema(description = "User's first name", example = "John", required = true)
    private String name;
    
    @Schema(description = "User's last name", example = "Doe", required = true)
    private String lastname;
    
    @Schema(description = "User's phone number", example = "+1234567890", required = true)
    private String phone;
    
    @Schema(description = "User's email address", example = "john@example.com", required = true)
    private String email;
    
    @Schema(description = "User's password", example = "password123", required = true)
    private String password;
    
    // Constructors
    public RegisterRequest() {}
    
    public RegisterRequest(String name, String lastname, String phone, String email, String password) {
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getLastname() {
        return lastname;
    }
    
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
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
