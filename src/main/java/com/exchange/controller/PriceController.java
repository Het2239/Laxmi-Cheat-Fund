package com.exchange.controller;

import com.exchange.service.PriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// PriceController - handles price queries
@RestController
@RequestMapping("/prices")
@CrossOrigin(origins = "*")
@Tag(name = "Prices", description = "Cryptocurrency price information")
public class PriceController {
    
    @Autowired
    private PriceService priceService;
    
    // Get all prices
    @GetMapping
    @Operation(
        summary = "Get all cryptocurrency prices", 
        description = "Retrieve current prices for all supported cryptocurrencies (BTC, ETH, USDT, SOL)"
    )
    public ResponseEntity<?> getAllPrices() {
        try {
            Map<String, Double> prices = priceService.getAllPrices();
            return ResponseEntity.ok(prices);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // Get price for a specific currency
    @GetMapping("/{currency}")
    @Operation(
        summary = "Get price for specific currency", 
        description = "Retrieve current price for a specific cryptocurrency"
    )
    public ResponseEntity<?> getPrice(@PathVariable String currency) {
        try {
            double price = priceService.getPrice(currency);
            
            return ResponseEntity.ok(Map.of(
                "currency", currency,
                "price", price
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
