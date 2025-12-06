package com.exchange.model.wallet;

import com.exchange.model.Wallet;

// UsdtWallet - USDT stablecoin wallet implementation
public class UsdtWallet extends Wallet {
    
    private static final String CURRENCY = "USDT";
    private static final String SYMBOL = "₮";
    private static final double MIN_BALANCE = 0.0;

    public UsdtWallet() {
        super();
        this.currency = CURRENCY;
    }

    public UsdtWallet(String address) {
        super(address, CURRENCY);
    }

    public UsdtWallet(String address, double balance) {
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
        return "Tether";
    }
}
