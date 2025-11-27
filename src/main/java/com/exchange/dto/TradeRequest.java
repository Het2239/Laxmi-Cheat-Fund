package com.exchange.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Trade request for buying or selling crypto")
public class TradeRequest {
    
    @Schema(description = "Cryptocurrency symbol", example = "BTC", required = true, allowableValues = {"BTC", "ETH", "USDT", "SOL"})
    private String currency;
    
    @Schema(description = "Amount to buy or sell", example = "0.1", required = true)
    private double amount;
    
    // Constructors
    public TradeRequest() {}
    
    public TradeRequest(String currency, double amount) {
        this.currency = currency;
        this.amount = amount;
    }
    
    // Getters and Setters
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
