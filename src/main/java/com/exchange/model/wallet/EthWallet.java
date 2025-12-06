package com.exchange.model.wallet;

import com.exchange.model.Wallet;

// EthWallet - Ethereum wallet implementation
public class EthWallet extends Wallet {
    
    private static final String CURRENCY = "ETH";
    private static final String SYMBOL = "Ξ";
    private static final double MIN_BALANCE = 0.0;

    public EthWallet() {
        super();
        this.currency = CURRENCY;
    }

    public EthWallet(String address) {
        super(address, CURRENCY);
    }

    public EthWallet(String address, double balance) {
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
        return "Ethereum";
    }
}
