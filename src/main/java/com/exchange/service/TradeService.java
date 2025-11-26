package com.exchange.service;

import com.exchange.model.BuySell;
import com.exchange.model.Transaction;
import com.exchange.model.User;
import com.exchange.repository.BuySellRepository;
import com.exchange.repository.ProfileRepository;
import com.exchange.repository.TransactionRepository;
import com.exchange.utils.LogWriter;
import com.exchange.utils.PriceSimulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TradeService - business logic for trading operations
@Service
public class TradeService {
    
    @Autowired
    private ProfileRepository profileRepository;
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private BuySellRepository buySellRepository;
    
    @Autowired
    private WalletService walletService;
    
    // Buy cryptocurrency
    public Transaction buyCrypto(String email, String currency, double amount) {
        if (amount <= 0) {
            throw new RuntimeException("Amount must be positive");
        }
        
        User user = profileRepository.findUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        
        // Get current price
        double currentPrice = PriceSimulator.getPrice(currency);
        
        // Calculate total cost in USD
        double totalCost = amount * currentPrice;
        
        // Check if user has sufficient USD balance
        if (!user.hasSufficientBalance("USD", totalCost)) {
            throw new RuntimeException("Insufficient USD balance. Required: " + totalCost);
        }
        
        // Deduct USD
        user.deductBalance("USD", totalCost);
        
        // Add cryptocurrency
        user.addBalance(currency, amount);
        
        // Update price (price increases on buy)
        double newPrice = PriceSimulator.updatePriceOnBuy(currency, amount);
        
        // Save user
        profileRepository.updateUser(user);
        
        // Create transaction
        Transaction transaction = new Transaction(
            "BUY",
            user.getAddress(),
            user.getAddress(),
            currency,
            amount,
            currentPrice
        );
        transaction.confirm();
        transactionRepository.saveTransaction(transaction);
        
        // Create buy/sell record
        BuySell buySell = new BuySell(
            user.getAddress(),
            "BUY",
            currency,
            amount,
            totalCost,
            currentPrice
        );
        buySellRepository.saveBuySell(buySell);
        
        // Log transaction
        LogWriter.logTransaction(user.getId(), "BUY", currency, amount, currentPrice, transaction.getId());
        
        return transaction;
    }
    
    // Sell cryptocurrency
    public Transaction sellCrypto(String email, String currency, double amount) {
        if (amount <= 0) {
            throw new RuntimeException("Amount must be positive");
        }
        
        User user = profileRepository.findUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        
        // Check if user has sufficient cryptocurrency balance
        if (!user.hasSufficientBalance(currency, amount)) {
            throw new RuntimeException("Insufficient " + currency + " balance");
        }
        
        // Get current price
        double currentPrice = PriceSimulator.getPrice(currency);
        
        // Calculate total USD to receive
        double totalUsd = amount * currentPrice;
        
        // Deduct cryptocurrency
        user.deductBalance(currency, amount);
        
        // Add USD
        user.addBalance("USD", totalUsd);
        
        // Update price (price decreases on sell)
        double newPrice = PriceSimulator.updatePriceOnSell(currency, amount);
        
        // Save user
        profileRepository.updateUser(user);
        
        // Create transaction
        Transaction transaction = new Transaction(
            "SELL",
            user.getAddress(),
            user.getAddress(),
            currency,
            amount,
            currentPrice
        );
        transaction.confirm();
        transactionRepository.saveTransaction(transaction);
        
        // Create buy/sell record
        BuySell buySell = new BuySell(
            user.getAddress(),
            "SELL",
            currency,
            amount,
            totalUsd,
            currentPrice
        );
        buySellRepository.saveBuySell(buySell);
        
        // Log transaction
        LogWriter.logTransaction(user.getId(), "SELL", currency, amount, currentPrice, transaction.getId());
        
        return transaction;
    }
    
    // Calculate cost for buying
    public double calculateBuyCost(String currency, double amount) {
        double price = PriceSimulator.getPrice(currency);
        return amount * price;
    }
    
    // Calculate revenue for selling
    public double calculateSellRevenue(String currency, double amount) {
        double price = PriceSimulator.getPrice(currency);
        return amount * price;
    }
}
