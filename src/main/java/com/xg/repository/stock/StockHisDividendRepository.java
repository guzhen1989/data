package com.xg.repository.stock;

import com.xg.api.model.stock.StockHisDividend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockHisDividendRepository extends
    JpaRepository<StockHisDividend,Integer> {



}
