package com.trading.paper_trading_engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // This enables background workers
public class PaperTradingEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaperTradingEngineApplication.class, args);
	}
}