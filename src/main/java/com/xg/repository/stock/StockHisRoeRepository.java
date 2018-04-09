package com.xg.repository.stock;

import com.xg.api.model.stock.StockHisRoe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockHisRoeRepository extends JpaRepository<StockHisRoe,Integer> {

}
