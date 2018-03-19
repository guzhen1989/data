package com.xg.config;

import com.xg.config.security.SecurityInterceptorFilter;
import com.xg.config.security.UserDetailService;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.annotation.Resource;

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

      @Resource
      private UserDetailService userDetailService;

      @Override
      protected UserDetailsService userDetailsService() {
          return userDetailService;
      }

      @Override
    protected void configure(HttpSecurity http) throws Exception {
//      http.authorizeRequests().and().formLogin().
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
