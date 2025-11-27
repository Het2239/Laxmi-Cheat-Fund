package com.exchange.model;

import java.time.LocalDateTime;
import java.util.UUID;

// BuySell - represents buy/sell operations
public class BuySell {
    private String id;
    private String userAddress;
    private String action; // BUY or SELL
    private String currency;
    private double amount;
    private double totalUsd;
    private double priceUsd;
    private String timestamp;

    public BuySell() {
        this.id = "bs-" + UUID.randomUUID().toString();
        this.timestamp = LocalDateTime.now().toString();
    }

    public BuySell(String userAddress, String action, String currency, 
                   double amount, double totalUsd, double priceUsd) {
        this.id = "bs-" + UUID.randomUUID().toString();
        this.userAddress = userAddress;
        this.action = action;
        this.currency = currency;
        this.amount = amount;
        this.totalUsd = totalUsd;
        this.priceUsd = priceUsd;
        this.timestamp = LocalDateTime.now().toString();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getTotalUsd() {
        return totalUsd;
    }

    public void setTotalUsd(double totalUsd) {
        this.totalUsd = totalUsd;
    }

    public double getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(double priceUsd) {
        this.priceUsd = priceUsd;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
