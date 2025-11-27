package com.exchange.utils;

// SecretKeyGenerator - generates secret keys for wallets
public class SecretKeyGenerator {
    
    public static String generateSecretKey(String name, String lastname, String email, String passwordHash) {
        // Option A: Simple deterministic secret key generation
        String combined = name + lastname + email + passwordHash;
        return HashUtil.sha256(combined);
    }
    
    // Alternative: Generate a simple mnemonic (4-word style)
    public static String generateMnemonic(String secretKey) {
        String[] words = {"apple", "banana", "cherry", "dragon", "eagle", "forest", 
                         "glass", "house", "island", "jungle", "king", "lion",
                         "moon", "night", "ocean", "planet", "queen", "river",
                         "star", "tree", "universe", "valley", "water", "xenon",
                         "yellow", "zebra"};
        
        // Use parts of the secret key to select 4 words
        StringBuilder mnemonic = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int index = Math.abs(secretKey.charAt(i * 8) % words.length);
            mnemonic.append(words[index]);
            if (i < 3) mnemonic.append("-");
        }
        return mnemonic.toString();
    }
}
