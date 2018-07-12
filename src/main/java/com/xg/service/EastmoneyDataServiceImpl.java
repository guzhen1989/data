package com.xg.service;

import com.xg.api.model.stock.StockCode;
import com.xg.api.model.stock.StockDateInfo;
import com.xg.util.StringUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 东方财富网
 *
 * @author guzhen
 * @date 2018/7/11
 */
@Service
@Transactional
public class EastmoneyDataServiceImpl implements RemoteData {

  private static final String EASTMONEY_STOCK_PATTERN = "(^.*)\\((\\d+)\\)$";

  @Override
  public List<StockCode> getStockCodeList() {
    List<StockCode> list = new ArrayList<>();
    Connection connect = Jsoup.connect("http://quote.eastmoney.com/stocklist.html");
    Document document = null;
    try {
      document = connect.get();
    } catch (IOException e) {
      return list;
    }
    Elements lis = document.body().select("#quotesearch ul li");
    for (Element li : lis) {
      StockCode stockCode = new StockCode();
      Element a = li.select("a").first();
      if (StringUtils.contains(a.attr("href"), "sh")) {
        stockCode.setExchange(StockCode.EXCHANGE_SH);
      } else {
        stockCode.setExchange(StockCode.EXCHANGE_SZ);
      }
      String[] regex = StringUtil.regex(EASTMONEY_STOCK_PATTERN, a.text());
      if (regex.length>1) {
        stockCode.setName(regex[0]);
        stockCode.setCode(regex[1]);
        list.add(stockCode);
      }
    }
    return list;
  }

  @Override
  public List<StockDateInfo> getDateInfoList(StockCode stockCode, Date start, Date end) {
    throw new UnsupportedOperationException("不支持");
  }
}
