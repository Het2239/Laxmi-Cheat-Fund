package com.exchange.service;

import com.exchange.model.User;
import com.exchange.repository.ProfileRepository;
import com.exchange.security.JwtUtil;
import com.exchange.utils.AddressGenerator;
import com.exchange.utils.HashUtil;
import com.exchange.utils.LogWriter;
import com.exchange.utils.SecretKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

// AuthService - business logic for authentication
@Service
public class AuthService {
    
    @Autowired
    private ProfileRepository profileRepository;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private PriceService priceService;
    
    @Autowired
    private WalletService walletService;
    
    // Register a new user
    public User register(String name, String lastname, String phone, String email, String password) {
        // Check if email already exists
        if (profileRepository.emailExists(email)) {
            throw new RuntimeException("Email already registered");
        }
        
        // Hash password
        String passwordHash = hashPassword(password);
        
        // Generate secret key
        String secretKey = SecretKeyGenerator.generateSecretKey(name, lastname, email, passwordHash);
        String secretKeyHash = HashUtil.sha256(secretKey);
        
        // Generate wallet address
        String address = AddressGenerator.generateAddress(secretKey);
        
        // Create user
        User user = new User(
            "user-" + UUID.randomUUID().toString(),
            name,
            lastname,
            email,
            phone,
            passwordHash,
            secretKeyHash,
            address
        );
        
        // Save user
        profileRepository.saveUser(user);
        
        // Log registration
        LogWriter.logAction(user.getId(), "REGISTER", "New user registered: " + email, "N/A");
        
        return user;
    }
    
    // Login user
    public String login(String email, String password) {
        // Find user by email
        User user = profileRepository.findUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("Invalid email or password");
        }
        
        // Verify password
        String passwordHash = hashPassword(password);
        if (!user.getPasswordHash().equals(passwordHash)) {
            throw new RuntimeException("Invalid email or password");
        }
        
        // Generate JWT token
        String token = jwtUtil.generateToken(email);
        
        // Log login
        LogWriter.logAction(user.getId(), "LOGIN", "User logged in: " + email, "N/A");
        
        return token;
    }
    
    // Hash password using SHA-256
    public String hashPassword(String password) {
        return HashUtil.sha256(password);
    }
    
    // Get user by email
    public User getUserByEmail(String email) {
        return profileRepository.findUserByEmail(email);
    }
    
    // Delete user profile with auto-liquidation
    public String deleteProfile(String email) {
        User user = profileRepository.findUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        
        // Liquidate all crypto holdings to USD
        double totalUsdValue = user.getBalance("USD");
        
        // Auto-liquidate all crypto holdings to USD
        for (String currency : new String[]{"BTC", "ETH", "USDT", "SOL"}) {
            Double balance = user.getBalance(currency);
            if (balance != null && balance > 0) {
                double currentPrice = priceService.getPrice(currency);
                double usdValue = balance * currentPrice;
                user.setBalance(currency, 0.0);
                user.addBalance("USD", usdValue);
                totalUsdValue += usdValue; // Update totalUsdValue with liquidated amount
                
                LogWriter.logAction(user.getId(), "AUTO_LIQUIDATE", 
                    String.format("Liquidated %.8f %s to %.2f USD at price %.2f", 
                        balance, currency, usdValue, currentPrice), "N/A");
            }
        }
        
        // Delete user
        boolean deleted = profileRepository.deleteUser(user.getId());
        
        if (deleted) {
            String message = String.format("Profile deleted. Total balance redeemed: %.2f USD", totalUsdValue);
            LogWriter.logAction(user.getId(), "DELETE_PROFILE", message, "N/A");
            return message;
        } else {
            throw new RuntimeException("Failed to delete profile");
        }
    }
}
