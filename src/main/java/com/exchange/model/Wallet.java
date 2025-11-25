package com.exchange.model;


import java.util.HashMap;
import java.util.Map;

// Wallet - represents a user's wallet
public class Wallet {
    // TODO: Implement Wallet model
    private String address;
    private Map<String , Double> balances = new HashMap<>();

    public Wallet(String address, Map<String, Double> balances) {
        this.address = address;
        this.balances = balances != null ? balances : new HashMap<>();
    }

    public Wallet() {
        this.balances = new HashMap<>();
    }

    // Getters and Setters
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
}
