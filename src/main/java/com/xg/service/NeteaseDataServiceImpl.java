package com.xg.service;

import com.xg.api.model.stock.StockCode;
import com.xg.api.model.stock.StockDateInfo;
import com.xg.feign.NeteaseDownloadApi;
import com.xg.util.CsvUtil;
import com.xg.util.DateFormatUtil;
import com.xg.util.StringUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
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
public class NeteaseDataServiceImpl implements RemoteData {

  @Resource
  private NeteaseDownloadApi neteaseDownloadApi;

  /**
   * http://tieba.baidu.com/p/4907062727
   * @return
   */
  /**
   * 标题：【网易股票数据api】#集搜客GooSeeker数据集开放目录#
   分类：API
   关键词：股票,实时数据,API接口
   摘要：可以查询单个、多个股票、历史数据、财务指标等等

   更多信息：
   一、单个股票实时查询
   【例子】工商银行股票代码0601398，股票代码之间使用逗号分隔，股票代码请去网易财经网查询。
   注：新浪和腾讯都用sh、sz来区分上证和深证，网易用的1和0来区分。

   http://api.money.126.net/data/feed/0601398,money.api


   二、多个股票实时查询
   http://api.money.126.net/data/feed/0601398,1000001,1000881,money.api


   三、历史数据下载（CSV格式）
   下面是获取工商银行0601398，从2008年07月20日到2015年05月08日之间的历史数据，文件为CSV格式
   http://quotes.money.163.com/service/chddata.html?code=0601398&start=20000720&end=20150508


   四、财务指标下载（CSV格式）
   http://quotes.money.163.com/service/zycwzb_601398.html?type=report


   五、利润表下载（CSV格式）
   http://quotes.money.163.com/service/lrb_601398.html


   六、现金流表（CSV格式）
   http://quotes.money.163.com/service/xjllb_601398.html


   * @return
   */

  @Override
  public List<StockCode> getStockCodeList() {
    throw new UnsupportedOperationException("不支持");
  }

  @Override
  public List<StockDateInfo> getDateInfoList(StockCode stockCode, Date start, Date end) {
    List<StockDateInfo> result=new ArrayList<>();
    String code=stockCode.getCode();
    if(StockCode.EXCHANGE_SH.equals(stockCode.getExchange())){
      code=0+code;
    }else {
      code=1+code;
    }
    ByteArrayOutputStream byteArrayOutputStream = neteaseDownloadApi.chddata(code,
        DateFormatUtil.format(start,DateFormatUtil.FORMAT_YYYYMMDD), DateFormatUtil.format(end,DateFormatUtil.FORMAT_YYYYMMDD));
    List<CSVRecord> csvRecords =
        CsvUtil.readCSV(
            byteArrayOutputStream.toByteArray(),
            Charset.forName("GBK"),
            "日期",
            "股票代码",
            "名称",
            "收盘价",
            "最高价",
            "最低价",
            "开盘价",
            "前收盘",
            "涨跌额",
            "涨跌幅",
            "换手率",
            "成交量",
            "成交金额",
            "总市值",
            "流通市值",
            "成交笔数");
    int i=0;
    for (CSVRecord csvRecord : csvRecords) {
      if (i==0){
        i++;
        continue;
      }
      StockDateInfo stockDateInfo=new StockDateInfo();
      stockDateInfo.setCode(csvRecord.get("股票代码"));
      stockDateInfo.setName(csvRecord.get("名称"));
      try {
        stockDateInfo.setDate(DateUtils.parseDate(csvRecord.get("日期"),DateFormatUtil.FORMAT_YYYY_MM_DD,DateFormatUtil.FORMAT_YYYY1MM1DD));
      } catch (ParseException e) {
        throw new RuntimeException("获取时间失败");
      }
      stockDateInfo.setOpenPrice(new BigDecimal(csvRecord.get("开盘价")));
      stockDateInfo.setClosePrice(new BigDecimal(csvRecord.get("收盘价")));
      stockDateInfo.setLowPrice(new BigDecimal(csvRecord.get("最低价")));
      stockDateInfo.setHighPrice(new BigDecimal(csvRecord.get("最高价")));
      stockDateInfo.setYestClosePrice(new BigDecimal(csvRecord.get("前收盘")));
      stockDateInfo.setChangeMoney(new BigDecimal(csvRecord.get("涨跌额")));
      stockDateInfo.setChangeRate(new BigDecimal(csvRecord.get("涨跌幅")));
      stockDateInfo.setTurnoverRate(new BigDecimal(csvRecord.get("换手率")));
      stockDateInfo.setVolumeCount(Long.parseLong(csvRecord.get("成交量")));
      stockDateInfo.setVolumeMoney(new BigDecimal(csvRecord.get("成交金额")));
      stockDateInfo.setTotalValue(new BigDecimal(csvRecord.get("总市值")));
      stockDateInfo.setCirculationMarketValue(new BigDecimal(csvRecord.get("流通市值")));
      result.add(stockDateInfo);
    }
    return result;
  }
}
