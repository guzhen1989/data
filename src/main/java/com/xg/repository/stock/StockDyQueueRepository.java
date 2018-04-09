package com.xg.repository.stock;

import com.xg.api.model.stock.StockDyQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockDyQueueRepository extends JpaRepository<StockDyQueue,Integer> {

}
