package com.exchange.service;

import com.exchange.model.User;
import com.exchange.model.Wallet;
import com.exchange.repository.ProfileRepository;
import com.exchange.utils.LogWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// WalletService - business logic for wallet operations
@Service
public class WalletService {
    
    @Autowired
    private ProfileRepository profileRepository;
    
    // Get all user wallets
    public List<Wallet> getAllWallets(String email) {
        User user = profileRepository.findUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        
        return user.getWallets();
    }
    
    // Get user wallet for specific currency
    public Wallet getUserWallet(String email, String currency) {
        User user = profileRepository.findUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        
        Wallet wallet = user.getWallet(currency);
        if (wallet == null) {
            throw new RuntimeException("Wallet not found for currency: " + currency);
        }
        
        return wallet;
    }
    
    // Get balance for a specific currency
    public double getBalance(String email, String currency) {
        User user = profileRepository.findUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        
        return user.getBalance(currency);
    }
    
    // Add USD balance (deposit)
    public void addUsdBalance(String email, double amount) {
        if (amount <= 0) {
            throw new RuntimeException("Amount must be positive");
        }
        
        User user = profileRepository.findUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        
        user.addBalance("USD", amount);
        profileRepository.updateUser(user);
        
        // Log the deposit
        LogWriter.logAction(user.getId(), "DEPOSIT", 
            String.format("Added %.2f USD to balance", amount), "N/A");
    }
    
    // Redeem USD balance (withdraw)
    public void redeemUsdBalance(String email, double amount) {
        if (amount <= 0) {
            throw new RuntimeException("Amount must be positive");
        }
        
        User user = profileRepository.findUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        
        if (!user.hasSufficientBalance("USD", amount)) {
            throw new RuntimeException("Insufficient USD balance");
        }
        
        user.deductBalance("USD", amount);
        profileRepository.updateUser(user);
        
        // Log the withdrawal
        LogWriter.logAction(user.getId(), "WITHDRAW", 
            String.format("Redeemed %.2f USD from balance", amount), "N/A");
    }
    
    // Update balance for a currency
    public void updateBalance(String email, String currency, double amount) {
        User user = profileRepository.findUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        
        user.setBalance(currency, amount);
        profileRepository.updateUser(user);
    }
    
    // Add balance for a currency
    public void addBalance(String email, String currency, double amount) {
        User user = profileRepository.findUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        
        user.addBalance(currency, amount);
        profileRepository.updateUser(user);
    }
    
    // Deduct balance for a currency
    public void deductBalance(String email, String currency, double amount) {
        User user = profileRepository.findUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        
        if (!user.hasSufficientBalance(currency, amount)) {
            throw new RuntimeException("Insufficient " + currency + " balance");
        }
        
        user.deductBalance(currency, amount);
        profileRepository.updateUser(user);
    }
}
