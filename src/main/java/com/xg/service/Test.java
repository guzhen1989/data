package com.xg.service;

import com.xg.api.model.stock.StockCode;
import java.io.IOException;
import java.util.Arrays;
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
 * 测试
 *
 * @author guzhen
 * @date 2018/7/11
 */
@Service
@Transactional
public class Test {

  private static final Pattern EASTMONEY_STOCK_PATTERN=Pattern.compile("(^.*)\\((\\d+)\\)$");

  public void test() throws IOException {
    //
    Connection connect = Jsoup.connect("http://quote.eastmoney.com/stocklist.html");
    Document document = connect.get();
    Elements lis = document.body().select("#quotesearch ul li");
    System.out.println(lis.size());
    for (Element li : lis) {
      //
      Element a = li.select("a").first();
      if(StringUtils.contains(a.attr("href"),"sh")){
        System.out.print("上海：");
      }else {
        System.out.print("深圳：");
      }
      String text=a.text();
      Matcher matcher = EASTMONEY_STOCK_PATTERN.matcher(text);
      if (matcher.find()) {
        System.out.print(matcher.group(1));
        System.out.print(":");
        System.out.println(matcher.group(2));
      }
    }
  }

  public static void main(String[] args) throws IOException {
    EastmoneyDataServiceImpl test=new EastmoneyDataServiceImpl();
    List<StockCode> list = test.getList();
    System.out.println(Arrays.toString(list.toArray()));
  }
}
