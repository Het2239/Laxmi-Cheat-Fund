package com.exchange.controller;

import com.exchange.dto.TradeRequest;
import com.exchange.model.Transaction;
import com.exchange.service.TradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

// TradeController - handles buy/sell operations
@RestController
@RequestMapping("/trade")
@CrossOrigin(origins = "*")
@Tag(name = "Trading", description = "Buy and sell cryptocurrency operations")
public class TradeController {
    
    @Autowired
    private TradeService tradeService;
    
    // Buy cryptocurrency
    @PostMapping("/buy")
    @Operation(
        summary = "Buy cryptocurrency", 
        description = "Purchase cryptocurrency using USD balance. Price increases based on trade volume.",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<?> buyCrypto(@RequestBody TradeRequest request, Authentication authentication) {
        try {
            String email = authentication.getName();
            
            // Validate input
            if (request.getCurrency() == null || request.getAmount() <= 0) {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid currency or amount"));
            }
            
            Transaction transaction = tradeService.buyCrypto(email, request.getCurrency(), request.getAmount());
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Crypto purchased successfully");
            response.put("transactionId", transaction.getId());
            response.put("currency", request.getCurrency());
            response.put("amount", request.getAmount());
            response.put("priceAtTrade", transaction.getPriceAtTradeUsd());
            response.put("totalCost", request.getAmount() * transaction.getPriceAtTradeUsd());
            response.put("status", transaction.getStatus());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // Sell cryptocurrency
    @PostMapping("/sell")
    @Operation(
        summary = "Sell cryptocurrency", 
        description = "Sell cryptocurrency and receive USD. Price decreases based on trade volume.",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<?> sellCrypto(@RequestBody TradeRequest request, Authentication authentication) {
        try {
            String email = authentication.getName();
            
            // Validate input
            if (request.getCurrency() == null || request.getAmount() <= 0) {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid currency or amount"));
            }
            
            Transaction transaction = tradeService.sellCrypto(email, request.getCurrency(), request.getAmount());
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Crypto sold successfully");
            response.put("transactionId", transaction.getId());
            response.put("currency", request.getCurrency());
            response.put("amount", request.getAmount());
            response.put("priceAtTrade", transaction.getPriceAtTradeUsd());
            response.put("totalRevenue", request.getAmount() * transaction.getPriceAtTradeUsd());
            response.put("status", transaction.getStatus());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
