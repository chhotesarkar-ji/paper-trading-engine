package com.trading.paper_trading_engine.service;

import com.trading.paper_trading_engine.model.MarketTick;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MarketDataService {

    // A thread-safe map to store our live stock prices in memory
    private final ConcurrentHashMap<String, Double> priceMap = new ConcurrentHashMap<>();
    private final Random random = new Random();

    // Constructor to set initial prices for our assets
    public MarketDataService() {
        priceMap.put("AAPL", 150.0);
        priceMap.put("MSFT", 420.0);
        priceMap.put("BTCUSD", 65000.0);
    }

    public MarketTick getLatestPrice(String symbol) {
        String upperSymbol = symbol.toUpperCase();

        // If the symbol isn't in our list, give it a default starting price
        if (!priceMap.containsKey(upperSymbol)) {
            priceMap.put(upperSymbol, 100.0);
        }

        double currentPrice = priceMap.get(upperSymbol);
        return new MarketTick(upperSymbol, currentPrice, System.currentTimeMillis());
    }

    // This background worker runs automatically every 1000 milliseconds (1 second)
    @Scheduled(fixedRate = 1000)
    public void simulateMarketMovement() {
        for (String symbol : priceMap.keySet()) {
            double currentPrice = priceMap.get(symbol);

            // Generate a random percentage change between -0.5% and +0.5%
            double percentChange = (random.nextDouble() - 0.5) * 0.01;
            double priceChange = currentPrice * percentChange;

            double newPrice = Math.round((currentPrice + priceChange) * 100.0) / 100.0;

            // Save the newly updated price back into memory
            priceMap.put(symbol, newPrice);
        }
    }
}