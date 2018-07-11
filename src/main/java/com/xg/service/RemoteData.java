package com.xg.service;

import com.xg.api.model.stock.StockCode;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author guzhen
 * @date 2018/7/11
 */
public interface RemoteData {

  List<StockCode> getList();

}
