package com.exchange.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exchange.model.Transaction;
import com.exchange.model.User;
import com.exchange.repository.ProfileRepository;
import com.exchange.repository.TransactionRepository;
import com.exchange.utils.LogWriter;

// TransactionService - business logic for transaction operations
@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private ProfileRepository profileRepository;
    

    // Log
    public void logTransaction(Transaction transaction) {
        transactionRepository.saveTransaction(transaction);
    }
    

    // Get transactions by user email
    public List<Transaction> getUserTransactions(String email) {
        User user = profileRepository.findUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        
        return transactionRepository.getTransactionsByAddress(user.getAddress());
    }
    

    
    
    public List<Transaction> getAllTransactions() {
        return transactionRepository.getAllTransactions();
    }
    
    // Transfer cryptocurrency between users
    public Transaction transferCrypto(String fromEmail, String toAddress, String currency, double amount) {
        if (amount <= 0) {
            throw new RuntimeException("Amount must be positive");
        }
        
        // Find sender
        User sender = profileRepository.findUserByEmail(fromEmail);
        if (sender == null) {
            throw new RuntimeException("Sender not found");
        }
        
        // Find receiver by address
        User receiver = profileRepository.findUserByAddress(toAddress);
        if (receiver == null) {
            throw new RuntimeException("Receiver address not found");
        }
        
        // Check if sender has sufficient balance
        if (!sender.hasSufficientBalance(currency, amount)) {
            throw new RuntimeException("Insufficient " + currency + " balance");
        }
        
        // minus from sender
        sender.deductBalance(currency, amount);
        profileRepository.updateUser(sender);
        
        // Add to receiver
        receiver.addBalance(currency, amount);
        profileRepository.updateUser(receiver);
        
        // Create transaction
        Transaction transaction = new Transaction(
            "TRANSFER",
            sender.getAddress(),
            receiver.getAddress(),
            currency,
            amount,
            null
        );
        transaction.confirm();
        transactionRepository.saveTransaction(transaction);
        
        // Log
        LogWriter.logTransaction(sender.getId(), "TRANSFER", currency, amount, null, transaction.getId());
        
        return transaction;
    }
}
