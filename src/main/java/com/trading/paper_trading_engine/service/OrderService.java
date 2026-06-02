package com.trading.paper_trading_engine.service;

import com.trading.paper_trading_engine.model.MarketTick;
import com.trading.paper_trading_engine.model.Portfolio;
import com.trading.paper_trading_engine.model.OrderRecord;
import com.trading.paper_trading_engine.repository.PortfolioRepository;
import com.trading.paper_trading_engine.repository.OrderRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private OrderRecordRepository orderRecordRepository;

    @Autowired
    private MarketDataService marketDataService;

    // --- BUY LOGIC ---
    public String buyStock(String username, String symbol, double quantity) {
        Portfolio portfolio = portfolioRepository.findById(username).orElse(null);
        if (portfolio == null) {
            return "Trade Failed: User not found!";
        }

        MarketTick liveTick = marketDataService.getLatestPrice(symbol);
        double executionPrice = liveTick.getCurrentPrice();
        double totalCost = executionPrice * quantity;

        if (portfolio.getBalance() < totalCost) {
            return "Trade Failed: Insufficient funds! Total cost is $" + String.format("%.2f", totalCost)
                    + " but your balance is only $" + String.format("%.2f", portfolio.getBalance());
        }

        portfolio.setBalance(portfolio.getBalance() - totalCost);

        if (symbol.equalsIgnoreCase("AAPL")) {
            portfolio.setAaplHoldings(portfolio.getAaplHoldings() + quantity);
        } else if (symbol.equalsIgnoreCase("BTCUSD")) {
            portfolio.setBtcHoldings(portfolio.getBtcHoldings() + quantity);
        } else {
            return "Trade Failed: We currently only support trading AAPL and BTCUSD!";
        }

        portfolioRepository.save(portfolio);

        OrderRecord receipt = new OrderRecord(null, username, symbol.toUpperCase(), "BUY", quantity, executionPrice, System.currentTimeMillis());
        orderRecordRepository.save(receipt);

        return "➔ TRADE SUCCESS! Bought " + quantity + " shares of " + symbol.toUpperCase()
                + " at $" + executionPrice + " per share. Total cost: $" + String.format("%.2f", totalCost);
    }

    // --- NEW: SELL LOGIC ---
    public String sellStock(String username, String symbol, double quantity) {
        // 1. Fetch the user's portfolio from the database
        Portfolio portfolio = portfolioRepository.findById(username).orElse(null);
        if (portfolio == null) {
            return "Trade Failed: User not found!";
        }

        // 2. Risk Management: Check if the user has enough shares/coins to sell
        if (symbol.equalsIgnoreCase("AAPL") && portfolio.getAaplHoldings() < quantity) {
            return "Trade Failed: Insufficient holdings! You want to sell " + quantity + " AAPL but only own " + portfolio.getAaplHoldings();
        } else if (symbol.equalsIgnoreCase("BTCUSD") && portfolio.getBtcHoldings() < quantity) {
            return "Trade Failed: Insufficient holdings! You want to sell " + quantity + " BTCUSD but only own " + portfolio.getBtcHoldings();
        }

        // 3. Fetch the absolute latest live market price
        MarketTick liveTick = marketDataService.getLatestPrice(symbol);
        double executionPrice = liveTick.getCurrentPrice();
        double totalRevenue = executionPrice * quantity;

        // 4. Update the portfolio values in memory
        portfolio.setBalance(portfolio.getBalance() + totalRevenue); // Cash goes UP

        if (symbol.equalsIgnoreCase("AAPL")) {
            portfolio.setAaplHoldings(portfolio.getAaplHoldings() - quantity); // Shares go DOWN
        } else if (symbol.equalsIgnoreCase("BTCUSD")) {
            portfolio.setBtcHoldings(portfolio.getBtcHoldings() - quantity); // Coins go DOWN
        } else {
            return "Trade Failed: Unsupported asset symbol!";
        }

        // 5. Commit changes back to the database
        portfolioRepository.save(portfolio);

        // 6. Write a "SELL" log into the audit trail history table
        OrderRecord receipt = new OrderRecord(
                null,
                username,
                symbol.toUpperCase(),
                "SELL",
                quantity,
                executionPrice,
                System.currentTimeMillis()
        );
        orderRecordRepository.save(receipt);

        return "➔ TRADE SUCCESS! Sold " + quantity + " shares of " + symbol.toUpperCase()
                + " at $" + executionPrice + " per share. Total revenue credited: $" + String.format("%.2f", totalRevenue);
    }
}