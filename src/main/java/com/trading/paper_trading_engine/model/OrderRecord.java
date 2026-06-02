package com.trading.paper_trading_engine.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;               // Auto-incrementing unique ID for every trade row

    private String username;       // The trader (e.g., "ChhoteSarkar")
    private String symbol;         // The asset traded (e.g., "AAPL")
    private String orderType;      // "BUY" or "SELL"
    private double quantity;       // Number of shares or coins
    private double executionPrice; // The price at the exact millisecond of execution
    private long timestamp;        // Epoch millisecond timestamp of the trade
}