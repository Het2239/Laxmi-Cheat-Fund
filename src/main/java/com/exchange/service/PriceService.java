package com.exchange.service;

import com.exchange.utils.PriceSimulator;
import org.springframework.stereotype.Service;

import java.util.Map;

// PriceService - business logic for price operations
@Service
public class PriceService {
    
    // Get price for a specific currency
    public double getPrice(String currency) {
        return PriceSimulator.getPrice(currency);
    }
    
    // Get all prices
    public Map<String, Double> getAllPrices() {
        return PriceSimulator.getAllPrices();
    }
    
    // Simulate price fluctuation (for testing)
    public double simulateFluctuation(String currency) {
        return PriceSimulator.simulateFluctuation(currency);
    }
}
