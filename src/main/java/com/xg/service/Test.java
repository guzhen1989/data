package com.xg.service;

import com.xg.api.model.stock.StockCode;
import com.xg.feign.NeteaseDownloadApi;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.Resource;
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
  @Resource
  private NeteaseDownloadApi neteaseDownloadApi;

  public void test() throws IOException {

  }

  public static void main(String[] args) throws IOException {
    EastmoneyDataServiceImpl test=new EastmoneyDataServiceImpl();
    List<StockCode> list = test.getStockCodeList();
    System.out.println(Arrays.toString(list.toArray()));
  }
}
