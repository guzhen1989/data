package com.xg.job;

import com.xg.api.model.stock.StockCode;
import com.xg.service.RemoteData;
import com.xg.service.StockCodeService;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 远程数据同步定时任务
 *
 * @author guzhen
 * @date 2018/7/11
 */
@Service
public class RemoteDataJob {

  private static final Logger LOGGER = LoggerFactory.getLogger(RemoteDataJob.class);

  @Resource private StockCodeService stockCodeService;

  @Resource
  private List<RemoteData> remoteDataList;

  @Scheduled(cron = "0 0 2 * * ?")
  public void syncStockCode(){
    LOGGER.info("同步stock code开始...");
    boolean cont=false;
    for (RemoteData remoteData : remoteDataList) {
      try{
        List<StockCode> list = remoteData.getList();
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
    LOGGER.info("同步stock code结束！");
  }

}
