package com.xg.feign;

import feign.Logger;
import feign.codec.Decoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * config
 *
 * @author guzhen
 * @date 2018/7/12
 */
@Configuration
public class FeignConfigration {

  @Bean
  public Decoder outputStreamDecoder() {
    return new ByteArrayOutputStreamDecoder();
  }
  @Bean
  public Logger.Level feignLoggerLevel() {
    return feign.Logger.Level.FULL;
  }
}
