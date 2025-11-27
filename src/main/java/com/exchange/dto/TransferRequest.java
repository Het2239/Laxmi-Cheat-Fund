package com.exchange.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Transfer request for sending crypto to another user")
public class TransferRequest {
    
    @Schema(description = "Recipient's wallet address", example = "d3f4e5a6b7c8d9e0f1a2", required = true)
    private String toAddress;
    
    @Schema(description = "Cryptocurrency symbol", example = "USDT", required = true, allowableValues = {"BTC", "ETH", "USDT", "SOL"})
    private String currency;
    
    @Schema(description = "Amount to transfer", example = "100.0", required = true)
    private double amount;
    
    // Constructors
    public TransferRequest() {}
    
    public TransferRequest(String toAddress, String currency, double amount) {
        this.toAddress = toAddress;
        this.currency = currency;
        this.amount = amount;
    }
    
    // Getters and Setters
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
}
