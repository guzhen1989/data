package com.xg.configuration.security;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web配置
 *
 * @author guzhen
 * @date 2018/3/29
 */
@Configuration
public class WebConfiguration {
//  @Bean
//  public WebMvcConfigurer corsConfigurer() {
//    return new WebMvcConfigurer() {
//      @Override
//      public void addCorsMappings(CorsRegistry registry) {
//        registry
//            .addMapping("/**")
//            .allowCredentials(true)
//            .allowedHeaders("*")
//            .allowedMethods("*")
//            .allowedOrigins("*");
//      }
//    };
//  }
}
