package com.exchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

// CryptoExchangeApplication - Main Spring Boot application
@SpringBootApplication
@EnableScheduling
public class CryptoExchangeApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(CryptoExchangeApplication.class, args);
        System.out.println("Crypto Exchange Application Started Successfully!");
    }
}
