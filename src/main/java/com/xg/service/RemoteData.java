package com.xg.service;

import com.xg.api.model.stock.StockCode;
import com.xg.api.model.stock.StockDateInfo;
import java.util.Date;
import java.util.List;
import javax.xml.crypto.Data;

/**
 * ${DESCRIPTION}
 *
 * @author guzhen
 * @date 2018/7/11
 */
public interface RemoteData {

  List<StockCode> getStockCodeList();

  List<StockDateInfo> getDateInfoList(StockCode stockCode,Date start,Date end);

}
