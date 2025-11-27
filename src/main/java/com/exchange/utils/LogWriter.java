package com.exchange.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// LogWriter - writes logs to log.txt
public class LogWriter {
    
    private static final String LOG_FILE = "data/log.txt";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    
    // Write a log message
    public static void writeLog(String message) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             PrintWriter pw = new PrintWriter(fw)) {
            
            String timestamp = LocalDateTime.now().format(formatter);
            pw.println(timestamp + " | " + message);
            
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
    
    // Log user action with details
    public static void logAction(String userId, String action, String details, String txId) {
        String message = String.format("INFO | %s | %s | %s | %s", 
            userId, action, details, txId);
        writeLog(message);
    }
    
    // Log transaction
    public static void logTransaction(String userId, String type, String currency, 
                                     double amount, Double price, String txId) {
        String priceStr = price != null ? String.format("@ %.2f USD", price) : "";
        String details = String.format("%s | %.6f %s %s", type, amount, currency, priceStr);
        logAction(userId, type, details, txId);
    }
}
