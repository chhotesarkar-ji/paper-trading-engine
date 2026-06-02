package com.trading.paper_trading_engine.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Portfolio {

    @Id
    private String username;       // Unique username for the virtual trader
    private double balance;        // Virtual cash balance (e.g., $100,000.00 USD)
    private double btcHoldings;    // Tracks amount of Bitcoin owned
    private double aaplHoldings;   // Tracks amount of Apple stock owned
}