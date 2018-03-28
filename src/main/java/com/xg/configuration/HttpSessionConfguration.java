package com.xg.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

/**
 * spring session
 *
 * @author guzhen
 * @date 2018/3/27
 */
@Configuration
public class HttpSessionConfguration {

  @Bean
  HttpSessionIdResolver headerHttpSessionIdResolver(){
    return HeaderHttpSessionIdResolver.xAuthToken();
  }

}
