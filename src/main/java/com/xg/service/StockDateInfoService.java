package com.xg.service;

import com.xg.api.model.stock.StockCode;
import com.xg.api.model.stock.StockDateInfo;
import com.xg.repository.StockDateInfoRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 日数据
 *
 * @author guzhen
 * @date 2018/7/12
 */
@Slf4j
@Service
@Transactional
public class StockDateInfoService {

  @Resource
  private StockDateInfoRepository stockDateInfoRepository;

  @Resource
  private StockCodeService stockCodeService;

  public void saveAll(List<StockDateInfo> list){
    stockDateInfoRepository.saveAll(list);
  }

  public void syncOne(String code,Date date,List<StockDateInfo> list){
    StockCode stockCode = stockCodeService.findById(code).get();
    stockCode.setDataDate(date);
    stockCodeService.save(stockCode);

    stockDateInfoRepository.saveAll(list);
  }
}
