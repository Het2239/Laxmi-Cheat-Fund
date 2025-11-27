package com.exchange.utils;

// AddressGenerator - generates wallet addresses
public class AddressGenerator {
    
    // Generate main wallet address from secret key
    public static String generateAddress(String secretKey) {
        String hash = HashUtil.sha256(secretKey + "WALLET");
        return hash.substring(0, 20);
    }
    
    // Generate currency-specific address
    public static String generateCurrencyAddress(String secretKey, String currency) {
        String hash = HashUtil.sha256(secretKey + currency);
        return hash.substring(0, 20);
    }
}
