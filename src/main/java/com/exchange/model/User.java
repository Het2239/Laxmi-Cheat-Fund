package com.exchange.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// User - represents a user in the system
public class User {
    private String id;
    private String name;
    private String lastname;
    private String email;
    private String phone;
    private String passwordHash;
    private String secretKeyHash;
    private String address;
    private Map<String, Double> balances;
    private String createdAt;

    public User() {
        this.balances = new HashMap<>();
        this.createdAt = LocalDateTime.now().toString();
    }

    public User(String id, String name, String lastname, String email, String phone, 
                String passwordHash, String secretKeyHash, String address) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.passwordHash = passwordHash;
        this.secretKeyHash = secretKeyHash;
        this.address = address;
        this.balances = new HashMap<>();
        this.createdAt = LocalDateTime.now().toString();
        
        // Initialize with zero balances
        this.balances.put("USD", 0.0);
        this.balances.put("BTC", 0.0);
        this.balances.put("ETH", 0.0);
        this.balances.put("USDT", 0.0);
        this.balances.put("SOL", 0.0);
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSecretKeyHash() {
        return secretKeyHash;
    }

    public void setSecretKeyHash(String secretKeyHash) {
        this.secretKeyHash = secretKeyHash;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<String, Double> getBalances() {
        return balances;
    }

    public void setBalances(Map<String, Double> balances) {
        this.balances = balances;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    // Utility methods for balance management
    public Double getBalance(String currency) {
        return balances.getOrDefault(currency, 0.0);
    }

    public void setBalance(String currency, Double amount) {
        balances.put(currency, amount);
    }

    public void addBalance(String currency, Double amount) {
        balances.put(currency, getBalance(currency) + amount);
    }

    public void deductBalance(String currency, Double amount) {
        balances.put(currency, getBalance(currency) - amount);
    }

    public boolean hasSufficientBalance(String currency, Double amount) {
        return getBalance(currency) >= amount;
    }
}
