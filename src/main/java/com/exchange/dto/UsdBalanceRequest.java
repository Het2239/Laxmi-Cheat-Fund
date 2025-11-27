package com.exchange.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "USD balance operation request")
public class UsdBalanceRequest {
    
    @Schema(description = "Amount of USD to add or redeem", example = "1000.00", required = true)
    private double amount;
    
    // Constructors
    public UsdBalanceRequest() {}
    
    public UsdBalanceRequest(double amount) {
        this.amount = amount;
    }
    
    // Getters and Setters
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
