package com.xg.config.security;

import javax.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * security 配置
 *
 * @author guzhen
 * @date 2018/3/19
 */
@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

  @Resource private SecurityInterceptorFilter securityInterceptorFilter;

  @Resource private UserDetailService userDetailService;

  @Resource private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .anyRequest()
        .permitAll() //所有请求通过
        .and()
        .formLogin()
        .loginProcessingUrl("/api/login")
        .and()
        .csrf()
        .disable();
    http.addFilterBefore(securityInterceptorFilter, FilterSecurityInterceptor.class);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailService).passwordEncoder(bCryptPasswordEncoder);
  }
}
