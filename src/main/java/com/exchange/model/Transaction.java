package com.exchange.model;

import java.time.LocalDateTime;
import java.util.UUID;

// Transaction - represents a transaction
public class Transaction {
    private String id;
    private String type; // BUY, SELL, TRANSFER, DEPOSIT, WITHDRAW
    private String fromAddress;
    private String toAddress;
    private String currency;
    private double amount;
    private Double priceAtTradeUsd;
    private String timestamp;
    private String status; // PENDING, CONFIRMED

    public Transaction() {
        this.id = "tx-" + UUID.randomUUID().toString();
        this.timestamp = LocalDateTime.now().toString();
        this.status = "PENDING";
    }

    public Transaction(String type, String fromAddress, String toAddress, 
                      String currency, double amount, Double priceAtTradeUsd) {
        this.id = "tx-" + UUID.randomUUID().toString();
        this.type = type;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.currency = currency;
        this.amount = amount;
        this.priceAtTradeUsd = priceAtTradeUsd;
        this.timestamp = LocalDateTime.now().toString();
        this.status = "PENDING";
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
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

    public Double getPriceAtTradeUsd() {
        return priceAtTradeUsd;
    }

    public void setPriceAtTradeUsd(Double priceAtTradeUsd) {
        this.priceAtTradeUsd = priceAtTradeUsd;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void confirm() {
        this.status = "CONFIRMED";
    }
}
