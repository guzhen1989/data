package com.xg.repository.stock;

import com.xg.api.model.stock.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock,String> {

}
