package com.trading.paper_trading_engine.service;

import com.trading.paper_trading_engine.model.Portfolio;
import com.trading.paper_trading_engine.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if our default user "ChhoteSarkar" already exists; if not, create them!
        if (!portfolioRepository.existsById("ChhoteSarkar")) {
            Portfolio defaultPortfolio = new Portfolio(
                    "ChhoteSarkar",
                    100000.00,  // $100,000.00 Virtual Cash balance
                    0.5,        // 0.5 BTC holdings
                    10.0        // 10 AAPL Shares holdings
            );

            portfolioRepository.save(defaultPortfolio);
            System.out.println("➔ DATABASE SUCCESS: Default trading portfolio created for ChhoteSarkar!");
        }
    }
}