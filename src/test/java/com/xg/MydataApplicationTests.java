package com.xg;

import com.xg.api.model.stock.StockDateInfo;
import com.xg.feign.NeteaseDownloadApi;
import com.xg.service.NeteaseDataServiceImpl;
import com.xg.service.StockCodeService;
import com.xg.util.CsvUtil;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MydataApplicationTests {

  @Autowired NeteaseDownloadApi neteaseDownloadApi;

  @Autowired
  NeteaseDataServiceImpl neteaseDataService;
  @Autowired
  StockCodeService stockCodeService;

  @Test
  public void contextLoads() throws FileNotFoundException {
    Date today=new Date();
    List<StockDateInfo> dateInfoList = neteaseDataService
        .getDateInfoList(stockCodeService.findById("601398").get(), DateUtils.addMonths(today, -10),
            today);
    for (StockDateInfo stockDateInfo : dateInfoList) {
      System.out.println(stockDateInfo);
    }
  }
}
