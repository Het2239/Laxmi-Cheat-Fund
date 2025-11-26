package com.exchange.repository;

import com.exchange.model.BuySell;
import com.exchange.utils.JsonFileUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

// BuySellRepository - handles buysell.json operations
@Repository
public class BuySellRepository {
    
    private static final String BUYSELL_FILE = "data/buysell.json";
    
    // Get all buy/sell records
    public List<BuySell> getAllBuySell() {
        return JsonFileUtil.readJsonArray(BUYSELL_FILE, BuySell.class);
    }
    
    // Save a new buy/sell record
    public void saveBuySell(BuySell buySell) {
        List<BuySell> records = getAllBuySell();
        records.add(buySell);
        saveBuySellList(records);
    }
    
    // Save buy/sell list
    public void saveBuySellList(List<BuySell> list) {
        JsonFileUtil.writeJsonArray(BUYSELL_FILE, list);
    }
    
    // Get buy/sell records by user address
    public List<BuySell> getBuySellByAddress(String address) {
        List<BuySell> records = getAllBuySell();
        return records.stream()
                .filter(bs -> bs.getUserAddress().equals(address))
                .collect(Collectors.toList());
    }
    
    // Get buy/sell records by user address and action
    public List<BuySell> getBuySellByAddressAndAction(String address, String action) {
        List<BuySell> records = getAllBuySell();
        return records.stream()
                .filter(bs -> bs.getUserAddress().equals(address) && bs.getAction().equals(action))
                .collect(Collectors.toList());
    }
    
    // Get buy/sell record by ID
    public BuySell getBuySellById(String id) {
        List<BuySell> records = getAllBuySell();
        return records.stream()
                .filter(bs -> bs.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
