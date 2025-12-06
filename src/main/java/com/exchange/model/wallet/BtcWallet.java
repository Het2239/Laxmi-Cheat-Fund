package com.exchange.model.wallet;

import com.exchange.model.Wallet;

// BtcWallet - Bitcoin wallet implementation
public class BtcWallet extends Wallet {
    
    private static final String CURRENCY = "BTC";
    private static final String SYMBOL = "₿";
    private static final double MIN_BALANCE = 0.0;

    public BtcWallet() {
        super();
        this.currency = CURRENCY;
    }

    public BtcWallet(String address) {
        super(address, CURRENCY);
    }

    public BtcWallet(String address, double balance) {
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
        return "Bitcoin";
    }
}
