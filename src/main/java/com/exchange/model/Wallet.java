package com.exchange.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.exchange.model.wallet.*;

import java.time.LocalDateTime;

// Abstract Wallet class - parent class for all currency-specific wallets
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = BtcWallet.class, name = "BTC"),
    @JsonSubTypes.Type(value = EthWallet.class, name = "ETH"),
    @JsonSubTypes.Type(value = UsdtWallet.class, name = "USDT"),
    @JsonSubTypes.Type(value = SolWallet.class, name = "SOL"),
    @JsonSubTypes.Type(value = UsdWallet.class, name = "USD")
})
public abstract class Wallet {
    protected String address;
    protected double balance;
    protected String currency;
    protected String createdAt;
    protected String lastUpdated;

    public Wallet() {
        this.balance = 0.0;
        this.createdAt = LocalDateTime.now().toString();
        this.lastUpdated = LocalDateTime.now().toString();
    }

    public Wallet(String address, String currency) {
        this.address = address;
        this.currency = currency;
        this.balance = 0.0;
        this.createdAt = LocalDateTime.now().toString();
        this.lastUpdated = LocalDateTime.now().toString();
    }

    public Wallet(String address, String currency, double balance) {
        this.address = address;
        this.currency = currency;
        this.balance = balance;
        this.createdAt = LocalDateTime.now().toString();
        this.lastUpdated = LocalDateTime.now().toString();
    }

    // Abstract methods - must be implemented by child classes
    // These are computed properties, not stored in JSON
    @JsonIgnore
    public abstract String getCurrencySymbol();
    
    @JsonIgnore
    public abstract double getMinimumBalance();
    
    @JsonIgnore
    public abstract String getWalletType();

    // Common wallet operations
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        this.balance += amount;
        this.lastUpdated = LocalDateTime.now().toString();
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (!hasSufficientBalance(amount)) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        this.balance -= amount;
        this.lastUpdated = LocalDateTime.now().toString();
    }

    public boolean hasSufficientBalance(double amount) {
        return this.balance >= amount;
    }

    public void setBalance(double balance) {
        this.balance = balance;
        this.lastUpdated = LocalDateTime.now().toString();
    }

    // Getters and Setters
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return String.format("%s Wallet [%s]: %.8f %s", 
            getWalletType(), address, balance, getCurrencySymbol());
    }
}
