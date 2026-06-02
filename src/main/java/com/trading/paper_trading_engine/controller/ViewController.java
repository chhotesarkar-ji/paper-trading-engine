package com.trading.paper_trading_engine.controller;

import com.trading.paper_trading_engine.repository.PortfolioRepository;
import com.trading.paper_trading_engine.repository.OrderRecordRepository;
import com.trading.paper_trading_engine.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private OrderRecordRepository orderRecordRepository;

    @Autowired
    private OrderService orderService;

    // Directs the browser to our main dashboard visual UI
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        // Inject current database states straight into our HTML frontend tags
        model.addAttribute("portfolio", portfolioRepository.findById("ChhoteSarkar").orElse(null));
        model.addAttribute("history", orderRecordRepository.findByUsernameOrderByTimestampDesc("ChhoteSarkar"));
        return "dashboard"; // Looks for a file named dashboard.html inside templates
    }

    // Handles form submissions from the web page to execute trades seamlessly
    @PostMapping("/dashboard/trade")
    public String handleTrade(
            @RequestParam String action,
            @RequestParam String symbol,
            @RequestParam double quantity,
            Model model) {

        if (action.equalsIgnoreCase("BUY")) {
            orderService.buyStock("ChhoteSarkar", symbol, quantity);
        } else if (action.equalsIgnoreCase("SELL")) {
            orderService.sellStock("ChhoteSarkar", symbol, quantity);
        }

        return "redirect:/dashboard"; // Refresh page instantly to show updated values!
    }
}