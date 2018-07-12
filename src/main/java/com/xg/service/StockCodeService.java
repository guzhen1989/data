package com.xg.service;

import com.xg.api.model.stock.StockCode;
import com.xg.repository.StockCodeRepository;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 码
 *
 * @author guzhen
 * @date 2018/7/11
 */
@Service
@Transactional
@Slf4j
public class StockCodeService {

  private static final Date DEFAULT_DATA_DATE;

  static {
    Calendar instance = Calendar.getInstance();
    instance.set(1990, Calendar.JANUARY, 1);
    DEFAULT_DATA_DATE = instance.getTime();
  }

  @Resource private StockCodeRepository stockCodeRepository;

  public List<StockCode> queryAll(){
    return stockCodeRepository.findAll();
  }
  public void save(StockCode stockCode){
     stockCodeRepository.save(stockCode);
  }

  public Optional<StockCode> findById(String code){
    return stockCodeRepository.findById(code);
  }

  public void syncStockCode(StockCode stockCode) {
    Optional<StockCode> optional = stockCodeRepository.findById(stockCode.getCode());
    if (optional.isPresent()) {
      StockCode sc = optional.get();
      if (!StringUtils.equals(stockCode.getName(), sc.getName())) {
        sc.setName(stockCode.getName());
        stockCodeRepository.save(sc);
      }
    } else {
      stockCode.setDataDate(DEFAULT_DATA_DATE);
      stockCodeRepository.save(stockCode);
    }
  }
}
