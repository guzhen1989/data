package com.xg.service;

import com.xg.api.model.stock.StockCode;
import java.io.IOException;
import java.util.ArrayList;
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

  private static final Pattern EASTMONEY_STOCK_PATTERN = Pattern.compile("(^.*)\\((\\d+)\\)$");

  @Override
  public List<StockCode> getList() {
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
      String text = a.text();
      Matcher matcher = EASTMONEY_STOCK_PATTERN.matcher(text);
      if (matcher.find()&&matcher.groupCount()>1) {
        stockCode.setName(matcher.group(1));
        stockCode.setCode(matcher.group(2));
        list.add(stockCode);
      }
    }
    return list;
  }
}
