package com.xg.job;

import com.xg.api.model.stock.StockCode;
import com.xg.api.model.stock.StockDateInfo;
import com.xg.service.RemoteData;
import com.xg.service.StockCodeService;
import com.xg.service.StockDateInfoService;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 远程数据同步定时任务
 *
 * @author guzhen
 * @date 2018/7/11
 */
@Service
@Slf4j
public class RemoteDataJob {
  @Resource private StockCodeService stockCodeService;

  @Resource
  private List<RemoteData> remoteDataList;
  @Resource
  private StockDateInfoService stockDateInfoService;

  @Scheduled(cron = "0 0 2 * * ?")
  public void syncStockCode(){
    log.info("同步stock code开始...");
    boolean cont=false;
    for (RemoteData remoteData : remoteDataList) {
      try{
        List<StockCode> list = remoteData.getStockCodeList();
        for (StockCode stockCode : list) {
          stockCodeService.syncStockCode(stockCode);
        }
      }catch (UnsupportedOperationException e){
        cont=true;
      }
      if(!cont){
        break;
      }
    }
    log.info("同步stock code结束！");
  }

//  @Scheduled(cron = "0 30 2 * * ?")
  @Scheduled(fixedDelay = 5000L)
  public void syncStockDateInfo(){
    log.info("同步StockDateInfo开始...");
    boolean cont=false;
    Date today=new Date();
    for (RemoteData remoteData : remoteDataList) {
      try{
        List<StockCode> stockCodes = stockCodeService.queryAll();
        for (StockCode stockCode : stockCodes) {
          try{
            if(DateUtils.isSameDay(stockCode.getDataDate(),today)){
              continue;
            }
            Date start=DateUtils.addDays(stockCode.getDataDate(),1);
            List<StockDateInfo> list = remoteData.getDateInfoList(stockCode,start,today);
            if(CollectionUtils.isNotEmpty(list)){
              stockDateInfoService.syncOne(stockCode.getCode(),today,list);
            }
          }catch (Exception e){
            if(e instanceof UnsupportedOperationException){
              throw e;
            }
            log.warn(e.getMessage(),e);
          }

        }
      }catch (UnsupportedOperationException e){
        cont=true;
      }
      if(!cont){
        break;
      }
    }
    log.info("同步StockDateInfo结束！");
  }
}
