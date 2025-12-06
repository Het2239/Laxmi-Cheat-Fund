package com.exchange.model.wallet;

import com.exchange.model.Wallet;

// UsdWallet - USD fiat wallet implementation
public class UsdWallet extends Wallet {
    
    private static final String CURRENCY = "USD";
    private static final String SYMBOL = "$";
    private static final double MIN_BALANCE = 0.0;

    public UsdWallet() {
        super();
        this.currency = CURRENCY;
    }

    public UsdWallet(String address) {
        super(address, CURRENCY);
    }

    public UsdWallet(String address, double balance) {
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
        return "US Dollar";
    }
}
