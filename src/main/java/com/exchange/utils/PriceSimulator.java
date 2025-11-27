package com.exchange.utils;

import com.fasterxml.jackson.core.type.TypeReference;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// PriceSimulator - simulates crypto prices with buy/sell impact
public class PriceSimulator {
    
    private static final String PRICES_FILE = "data/current_prices.json";
    
    // Base prices (fallback if file doesn't exist)
    private static final Map<String, Double> BASE_PRICES = new HashMap<String, Double>() {{
        put("BTC", 95000.0);
        put("ETH", 3500.0);
        put("USDT", 1.0);
        put("SOL", 150.0);
    }};
    
    // Get current price for a currency
    public static double getPrice(String currency) {
        Map<String, Object> priceData = loadPrices();
        Object price = priceData.get(currency);
        
        if (price instanceof Number) {
            return ((Number) price).doubleValue();
        }
        
        // Fallback to base price
        return BASE_PRICES.getOrDefault(currency, 0.0);
    }
    
    // Get all current prices
    public static Map<String, Double> getAllPrices() {
        Map<String, Object> priceData = loadPrices();
        Map<String, Double> prices = new HashMap<>();
        
        for (String currency : BASE_PRICES.keySet()) {
            Object price = priceData.get(currency);
            if (price instanceof Number) {
                prices.put(currency, ((Number) price).doubleValue());
            } else {
                prices.put(currency, BASE_PRICES.get(currency));
            }
        }
        
        return prices;
    }
    
    // Update price based on buy action (price increases)
    public static double updatePriceOnBuy(String currency, double amount) {
        double currentPrice = getPrice(currency);
        
        // Price impact: larger trades have more impact
        // Impact = 0.1% to 2% based on trade size
        double impactFactor = Math.min(0.02, 0.001 + (amount * 0.0001));
        double newPrice = currentPrice * (1 + impactFactor);
        
        savePrice(currency, newPrice);
        return newPrice;
    }
    
    // Update price based on sell action (price decreases)
    public static double updatePriceOnSell(String currency, double amount) {
        double currentPrice = getPrice(currency);
        
        // Price impact: larger trades have more impact
        // Impact = 0.1% to 2% based on trade size
        double impactFactor = Math.min(0.02, 0.001 + (amount * 0.0001));
        double newPrice = currentPrice * (1 - impactFactor);
        
        savePrice(currency, newPrice);
        return newPrice;
    }
    
    // Simulate random price fluctuation (for background updates)
    public static double simulateFluctuation(String currency) {
        double currentPrice = getPrice(currency);
        
        // Random fluctuation: +/- 2%
        double fluctuation = (Math.random() - 0.5) * 0.02;
        double newPrice = currentPrice * (1 + fluctuation);
        
        savePrice(currency, newPrice);
        return newPrice;
    }
    
    // Load prices from file
    private static Map<String, Object> loadPrices() {
        try {
            Map<String, Object> prices = JsonFileUtil.readJsonWithType(
                PRICES_FILE, 
                new TypeReference<Map<String, Object>>() {}
            );
            
            if (prices == null) {
                return initializePrices();
            }
            return prices;
        } catch (Exception e) {
            return initializePrices();
        }
    }
    
    // Save price to file
    private static void savePrice(String currency, double price) {
        Map<String, Object> priceData = loadPrices();
        priceData.put(currency, price);
        priceData.put("lastUpdated", LocalDateTime.now().toString());
        
        JsonFileUtil.writeJsonFile(PRICES_FILE, priceData);
    }
    
    // Initialize prices file with base prices
    private static Map<String, Object> initializePrices() {
        Map<String, Object> priceData = new HashMap<>();
        priceData.putAll(BASE_PRICES);
        priceData.put("lastUpdated", LocalDateTime.now().toString());
        
        try {
            JsonFileUtil.writeJsonFile(PRICES_FILE, priceData);
        } catch (Exception e) {
            // Ignore if file cannot be created
        }
        
        return priceData;
    }
}
