package com.exchange.repository;

import com.exchange.model.Transaction;
import com.exchange.utils.JsonFileUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

// TransactionRepository - handles transactions.json operations
@Repository
public class TransactionRepository {
    
    private static final String TRANSACTIONS_FILE = "data/transactions.json";
    
    // Get all transactions
    public List<Transaction> getAllTransactions() {
        return JsonFileUtil.readJsonArray(TRANSACTIONS_FILE, Transaction.class);
    }
    
    // Save a new transaction
    public void saveTransaction(Transaction transaction) {
        List<Transaction> transactions = getAllTransactions();
        transactions.add(transaction);
        saveTransactionList(transactions);
    }
    
    // Save transaction list
    public void saveTransactionList(List<Transaction> transactions) {
        JsonFileUtil.writeJsonArray(TRANSACTIONS_FILE, transactions);
    }
    
    // Get transactions by address (from or to)
    public List<Transaction> getTransactionsByAddress(String address) {
        List<Transaction> transactions = getAllTransactions();
        return transactions.stream()
                .filter(tx -> address.equals(tx.getFromAddress()) || address.equals(tx.getToAddress()))
                .collect(Collectors.toList());
    }
    
    // Get transactions by user address and type
    public List<Transaction> getTransactionsByAddressAndType(String address, String type) {
        List<Transaction> transactions = getAllTransactions();
        return transactions.stream()
                .filter(tx -> (address.equals(tx.getFromAddress()) || address.equals(tx.getToAddress())) 
                        && type.equals(tx.getType()))
                .collect(Collectors.toList());
    }
    
    // Get transaction by ID
    public Transaction getTransactionById(String txId) {
        List<Transaction> transactions = getAllTransactions();
        return transactions.stream()
                .filter(tx -> tx.getId().equals(txId))
                .findFirst()
                .orElse(null);
    }
    
    // Update transaction status
    public void updateTransactionStatus(String txId, String status) {
        List<Transaction> transactions = getAllTransactions();
        List<Transaction> updated = transactions.stream()
                .map(tx -> {
                    if (tx.getId().equals(txId)) {
                        tx.setStatus(status);
                    }
                    return tx;
                })
                .collect(Collectors.toList());
        saveTransactionList(updated);
    }
}
