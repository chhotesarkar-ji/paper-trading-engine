package com.trading.paper_trading_engine.repository;

import com.trading.paper_trading_engine.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, String> {
    // JpaRepository automatically handles saving and loading data behind the scenes!
}