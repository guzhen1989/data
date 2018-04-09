package com.xg.repository.stock;

import com.xg.api.model.stock.StockHisPe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockHisPeRepository extends JpaRepository<StockHisPe,Integer> {

}
