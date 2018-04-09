package com.xg.repository.stock;

import com.xg.api.model.stock.StockHisPb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockHisPbRepository extends JpaRepository<StockHisPb,Integer> {

}
