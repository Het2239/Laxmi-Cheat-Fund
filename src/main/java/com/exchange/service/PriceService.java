package com.exchange.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.exchange.utils.JsonFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// PriceService - Real-world price fetching with CoinGecko API and trade impact
@Service
public class PriceService {

    private static final Logger logger = LoggerFactory.getLogger(PriceService.class);
    
    private static final String COINGECKO_API = "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin,ethereum,tether,solana&vs_currencies=usd";
    private static final String FILE_PATH = "data/current_prices.json";
    
    // Configuration parameters
    private static final double GRAVITY_STRENGTH = 0.10;  // 10% correction toward real price
    private static final double NORMALIZATION_THRESHOLD = 0.02;  // Only normalize if difference > 5%
    private static final double TRADE_SENSITIVITY = 0.0005;  // Price impact per unit traded
    private static final double MAX_TRADE_IMPACT = 0.05;  // Cap at 5% per trade

    // Thread-safe price storage
    private final Map<String, Double> internalPrices = new ConcurrentHashMap<>();
    private final Map<String, Double> realWorldPrices = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    public PriceService() {
        logger.info("Initializing PriceService...");
        
        // 1. Load existing prices from file
        loadPricesFromFile();

        // 2. If file was empty or missing, set defaults
        if (internalPrices.isEmpty()) {
            logger.info("No existing prices found, setting defaults");
            internalPrices.put("BTC", 95000.0);
            internalPrices.put("ETH", 3500.0);
            internalPrices.put("USDT", 1.0);
            internalPrices.put("SOL", 150.0);
            savePricesToFile();
        }

        // 3. Fetch real prices immediately on startup
        fetchRealWorldPrices();
    }

    // =================================================================
    // CORE FEATURE: Fetch Real Prices & Normalize (The "Gravity" Logic)
    // =================================================================

    // Runs every 30 seconds (30000 ms)
    @Scheduled(fixedRate = 30000)
    public void fetchRealWorldPrices() {
        try {
            logger.debug("Fetching real-world prices from CoinGecko...");
            
            // Fetch live data from CoinGecko
            String response = restTemplate.getForObject(COINGECKO_API, String.class);
            JsonNode root = objectMapper.readTree(response);

            if (root != null) {
                // Parse response and map to our currency codes
                double realBtc = root.path("bitcoin").path("usd").asDouble();
                double realEth = root.path("ethereum").path("usd").asDouble();
                double realUsdt = root.path("tether").path("usd").asDouble();
                double realSol = root.path("solana").path("usd").asDouble();

                realWorldPrices.put("BTC", realBtc);
                realWorldPrices.put("ETH", realEth);
                realWorldPrices.put("USDT", realUsdt);
                realWorldPrices.put("SOL", realSol);

                logger.info("✓ Synced with Market: BTC=${}, ETH=${}, USDT=${}, SOL=${}", 
                    String.format("%.2f", realBtc),
                    String.format("%.2f", realEth),
                    String.format("%.4f", realUsdt),
                    String.format("%.2f", realSol)
                );

                // Trigger the "Rubber Band" effect
                normalizePrices();
            }
        } catch (Exception e) {
            logger.warn("⚠ Could not fetch real prices (Offline mode): {}", e.getMessage());
        }
    }

    private void normalizePrices() {
        for (String currency : internalPrices.keySet()) {
            Double realPrice = realWorldPrices.get(currency);
            Double internalPrice = internalPrices.get(currency);

            if (realPrice != null && internalPrice != null && realPrice > 0) {
                // Calculate how far off we are (percentage error)
                double diffPercent = Math.abs((internalPrice - realPrice) / realPrice);

                if (diffPercent > NORMALIZATION_THRESHOLD) {
                    // Formula: NewPrice = Internal * (1 - gravity) + Real * gravity
                    double newPrice = (internalPrice * (1 - GRAVITY_STRENGTH)) + (realPrice * GRAVITY_STRENGTH);
                    internalPrices.put(currency, newPrice);
                    
                    logger.info("⚖ Normalizing {}: ${} → ${} (Real: ${})", 
                        currency,
                        String.format("%.2f", internalPrice),
                        String.format("%.2f", newPrice),
                        String.format("%.2f", realPrice)
                    );
                }
            }
        }
        savePricesToFile();
    }

    // =================================================================
    // CORE FEATURE: Trade Impact (Supply & Demand)
    // =================================================================

    public void applyTradeImpact(String currency, String action, double amount) {
        if (!internalPrices.containsKey(currency)) {
            logger.warn("Unknown currency for trade impact: {}", currency);
            return;
        }

        double currentPrice = internalPrices.get(currency);
        
        // Calculate impact based on trade size
        double changeFactor = amount * TRADE_SENSITIVITY;
        
        // Cap the single-trade impact to prevent exploitation
        if (changeFactor > MAX_TRADE_IMPACT) {
            changeFactor = MAX_TRADE_IMPACT;
        }

        double newPrice = currentPrice;
        
        if (action.equalsIgnoreCase("BUY")) {
            // Buying increases price
            newPrice = currentPrice * (1 + changeFactor);
            logger.info("📈 Trade Impact (BUY): {} price ${} → ${} (+{:.2f}%)", 
                currency,
                String.format("%.2f", currentPrice),
                String.format("%.2f", newPrice),
                changeFactor * 100
            );
        } else if (action.equalsIgnoreCase("SELL")) {
            // Selling decreases price
            newPrice = currentPrice * (1 - changeFactor);
            logger.info("📉 Trade Impact (SELL): {} price ${} → ${} (-{:.2f}%)", 
                currency,
                String.format("%.2f", currentPrice),
                String.format("%.2f", newPrice),
                changeFactor * 100
            );
        }

        internalPrices.put(currency, newPrice);
        savePricesToFile();
    }

    // =================================================================
    // GETTERS & FILE IO
    // =================================================================

    public double getPrice(String currency) {
        return internalPrices.getOrDefault(currency, 0.0);
    }

    public Map<String, Double> getAllPrices() {
        return new ConcurrentHashMap<>(internalPrices);
    }

    private void savePricesToFile() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File(FILE_PATH), internalPrices);
        } catch (IOException e) {
            logger.error("Error saving prices to file", e);
        }
    }

    private void loadPricesFromFile() {
        try {
            File file = new File(FILE_PATH);
            if (file.exists()) {
                Map<String, Double> loadedData = objectMapper.readValue(
                    file, 
                    new TypeReference<Map<String, Double>>() {}
                );
                internalPrices.putAll(loadedData);
                logger.info("Loaded {} prices from file", internalPrices.size());
            }
        } catch (IOException e) {
            logger.info("No existing price file found, starting fresh");
        }
    }
}
