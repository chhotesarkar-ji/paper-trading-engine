package com.trading.paper_trading_engine.repository;

import com.trading.paper_trading_engine.model.OrderRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRecordRepository extends JpaRepository<OrderRecord, Long> {

    // Spring Data JPA automatically converts this method name into a custom SQL query!
    // It will fetch all records for a specific username and sort them newest first.
    List<OrderRecord> findByUsernameOrderByTimestampDesc(String username);
}