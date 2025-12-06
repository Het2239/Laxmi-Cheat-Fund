package com.exchange.model;

import com.exchange.model.wallet.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private List<Wallet> wallets;
    private String createdAt;

    public User() {
        this.wallets = new ArrayList<>();
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
        this.wallets = new ArrayList<>();
        this.createdAt = LocalDateTime.now().toString();
        
        // Initialize wallets for all supported currencies
        initializeWallets();
    }

    // Initialize all currency wallets with zero balance
    private void initializeWallets() {
        this.wallets.add(new UsdWallet(this.address, 0.0));
        this.wallets.add(new BtcWallet(this.address, 0.0));
        this.wallets.add(new EthWallet(this.address, 0.0));
        this.wallets.add(new UsdtWallet(this.address, 0.0));
        this.wallets.add(new SolWallet(this.address, 0.0));
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

    public List<Wallet> getWallets() {
        return wallets;
    }

    public void setWallets(List<Wallet> wallets) {
        this.wallets = wallets;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    // Utility methods for wallet management
    public Wallet getWallet(String currency) {
        return wallets.stream()
                .filter(w -> w.getCurrency().equalsIgnoreCase(currency))
                .findFirst()
                .orElse(null);
    }

    public Double getBalance(String currency) {
        Wallet wallet = getWallet(currency);
        return wallet != null ? wallet.getBalance() : 0.0;
    }

    public void setBalance(String currency, Double amount) {
        Wallet wallet = getWallet(currency);
        if (wallet != null) {
            wallet.setBalance(amount);
        }
    }

    public void addBalance(String currency, Double amount) {
        Wallet wallet = getWallet(currency);
        if (wallet != null) {
            wallet.deposit(amount);
        }
    }

    public void deductBalance(String currency, Double amount) {
        Wallet wallet = getWallet(currency);
        if (wallet != null) {
            wallet.withdraw(amount);
        }
    }

    public boolean hasSufficientBalance(String currency, Double amount) {
        Wallet wallet = getWallet(currency);
        return wallet != null && wallet.hasSufficientBalance(amount);
    }
}
