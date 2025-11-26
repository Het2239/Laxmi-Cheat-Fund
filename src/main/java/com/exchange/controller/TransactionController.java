package com.exchange.controller;

import com.exchange.dto.TransferRequest;
import com.exchange.model.Transaction;
import com.exchange.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// TransactionController - handles transaction queries
@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "*")
@Tag(name = "Transactions", description = "Transaction history and crypto transfers")
public class TransactionController {
    
    @Autowired
    private TransactionService transactionService;
    
    // Get user transactions
    @GetMapping
    @Operation(
        summary = "Get transaction history", 
        description = "Retrieve all transactions for the authenticated user",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<?> getUserTransactions(Authentication authentication) {
        try {
            String email = authentication.getName();
            List<Transaction> transactions = transactionService.getUserTransactions(email);
            
            return ResponseEntity.ok(transactions);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // Transfer cryptocurrency to another user
    @PostMapping("/transfer")
    @Operation(
        summary = "Transfer cryptocurrency", 
        description = "Send cryptocurrency to another user's wallet address",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<?> transferCrypto(@RequestBody TransferRequest request, Authentication authentication) {
        try {
            String email = authentication.getName();
            
            // Validate input
            if (request.getToAddress() == null || request.getCurrency() == null || request.getAmount() <= 0) {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid input"));
            }
            
            Transaction transaction = transactionService.transferCrypto(
                email, 
                request.getToAddress(), 
                request.getCurrency(), 
                request.getAmount()
            );
            
            return ResponseEntity.ok(Map.of(
                "message", "Transfer successful",
                "transactionId", transaction.getId(),
                "currency", request.getCurrency(),
                "amount", request.getAmount(),
                "toAddress", request.getToAddress(),
                "status", transaction.getStatus()
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
