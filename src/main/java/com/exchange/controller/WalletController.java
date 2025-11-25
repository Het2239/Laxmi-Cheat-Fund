package com.exchange.controller;

import com.exchange.dto.UsdBalanceRequest;
import com.exchange.model.Wallet;
import com.exchange.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// WalletController - handles wallet operations
@RestController
@RequestMapping("/wallet")
@CrossOrigin(origins = "*")
@Tag(name = "Wallet", description = "Wallet management and balance operations")
public class WalletController {
    
    @Autowired
    private WalletService walletService;
    
    // Get user wallet
    @GetMapping
    @Operation(
        summary = "Get user wallet", 
        description = "Retrieve wallet details with all currency balances",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<?> getWallet(Authentication authentication) {
        try {
            String email = authentication.getName();
            Wallet wallet = walletService.getUserWallet(email);
            
            return ResponseEntity.ok(wallet);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // Get balance for a specific currency
    @GetMapping("/balance/{currency}")
    @Operation(
        summary = "Get balance for currency", 
        description = "Get balance for a specific cryptocurrency or USD",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<?> getBalance(@PathVariable String currency, Authentication authentication) {
        try {
            String email = authentication.getName();
            double balance = walletService.getBalance(email, currency);
            
            return ResponseEntity.ok(Map.of(
                "currency", currency,
                "balance", balance
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // Add USD balance (deposit)
    @PostMapping("/add-usd")
    @Operation(
        summary = "Add USD balance", 
        description = "Deposit USD to your wallet",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<?> addUsdBalance(@RequestBody UsdBalanceRequest request, Authentication authentication) {
        try {
            String email = authentication.getName();
            
            walletService.addUsdBalance(email, request.getAmount());
            
            return ResponseEntity.ok(Map.of(
                "message", "USD balance added successfully",
                "amount", request.getAmount()
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // Redeem USD balance (withdraw)
    @PostMapping("/redeem-usd")
    @Operation(
        summary = "Redeem USD balance", 
        description = "Withdraw USD from your wallet",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<?> redeemUsdBalance(@RequestBody UsdBalanceRequest request, Authentication authentication) {
        try {
            String email = authentication.getName();
            
            walletService.redeemUsdBalance(email, request.getAmount());
            
            return ResponseEntity.ok(Map.of(
                "message", "USD balance redeemed successfully",
                "amount", request.getAmount()
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
