package com.xg.config;

import com.xg.config.security.SecurityInterceptorFilter;
import javax.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * security 配置
 *
 * @author guzhen
 * @date 2018/3/19
 */
@Configuration
@EnableWebSecurity
public class WebSecurity {


  @Configuration
  @Order(1)
  public static class ApiSecurity extends WebSecurityConfigurerAdapter {
    @Resource
    private SecurityInterceptorFilter securityInterceptorFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
          .anyRequest().authenticated() //任何请求,登录后可以访问
          .and()
          .formLogin()
          .loginPage("/login")
          .failureUrl("/login?error")
          .permitAll() //登录页面用户任意访问
          .and()
          .logout().permitAll(); //注销行为任意访问
      http.addFilterBefore(securityInterceptorFilter, FilterSecurityInterceptor.class);
    }
  }
}
