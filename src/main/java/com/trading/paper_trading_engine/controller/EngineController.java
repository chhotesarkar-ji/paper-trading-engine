package com.trading.paper_trading_engine.controller;

import com.trading.paper_trading_engine.model.MarketTick;
import com.trading.paper_trading_engine.model.Portfolio;
import com.trading.paper_trading_engine.model.OrderRecord;
import com.trading.paper_trading_engine.service.MarketDataService;
import com.trading.paper_trading_engine.service.OrderService;
import com.trading.paper_trading_engine.repository.PortfolioRepository;
import com.trading.paper_trading_engine.repository.OrderRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EngineController {

    @Autowired
    private MarketDataService marketDataService;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRecordRepository orderRecordRepository;

    @GetMapping("/api/status")
    public String checkStatus() {
        return "Trading Engine is Live and Ready for Orders!";
    }

    @GetMapping("/api/price/{symbol}")
    public MarketTick getPrice(@PathVariable String symbol) {
        return marketDataService.getLatestPrice(symbol);
    }

    @GetMapping("/api/portfolio/{username}")
    public Portfolio getPortfolio(@PathVariable String username) {
        return portfolioRepository.findById(username).orElse(null);
    }

    @GetMapping("/api/trade/buy/{username}/{symbol}/{quantity}")
    public String placeBuyOrder(
            @PathVariable String username,
            @PathVariable String symbol,
            @PathVariable double quantity) {
        return orderService.buyStock(username, symbol, quantity);
    }

    // NEW: Web route to place a sell order
    @GetMapping("/api/trade/sell/{username}/{symbol}/{quantity}")
    public String placeSellOrder(
            @PathVariable String username,
            @PathVariable String symbol,
            @PathVariable double quantity) {
        return orderService.sellStock(username, symbol, quantity);
    }

    @GetMapping("/api/history/{username}")
    public List<OrderRecord> getOrderHistory(@PathVariable String username) {
        return orderRecordRepository.findByUsernameOrderByTimestampDesc(username);
    }
}