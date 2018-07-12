package com.xg.repository;

import com.xg.api.model.stock.StockCode;
import com.xg.api.model.stock.StockDateInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author guzhen
 * @date 2018/3/19
 */
@Repository
public interface StockDateInfoRepository extends JpaRepository<StockDateInfo, String> {

}
