package com.exchange.model.wallet;

import com.exchange.model.Wallet;

// SolWallet - Solana wallet implementation
public class SolWallet extends Wallet {
    
    private static final String CURRENCY = "SOL";
    private static final String SYMBOL = "◎";
    private static final double MIN_BALANCE = 0.0;

    public SolWallet() {
        super();
        this.currency = CURRENCY;
    }

    public SolWallet(String address) {
        super(address, CURRENCY);
    }

    public SolWallet(String address, double balance) {
        super(address, CURRENCY, balance);
    }

    @Override
    public String getCurrencySymbol() {
        return SYMBOL;
    }

    @Override
    public double getMinimumBalance() {
        return MIN_BALANCE;
    }

    @Override
    public String getWalletType() {
        return "Solana";
    }
}
