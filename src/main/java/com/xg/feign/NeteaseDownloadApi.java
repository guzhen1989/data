package com.xg.feign;

import java.io.ByteArrayOutputStream;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 网易数据
 *
 * @author guzhen
 * @date 2018/7/12
 */
@FeignClient(name = "neteaseDownloadApi",url = "http://quotes.money.163.com",configuration = FeignConfigration.class)
public interface NeteaseDownloadApi {

  @GetMapping(value = "/service/chddata.html")
  ByteArrayOutputStream chddata(@RequestParam("code") String code,@RequestParam("start")String start,@RequestParam("end")String end);

}
