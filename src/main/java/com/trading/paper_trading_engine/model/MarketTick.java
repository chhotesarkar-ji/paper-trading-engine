package com.trading.paper_trading_engine.model;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class MarketTick {
    private String symbol;      // The stock ticker, e.g., "AAPL" or "BTCUSD"
    private double currentPrice; // The current price
    private long timestamp;      // The exact millisecond the price updated
}