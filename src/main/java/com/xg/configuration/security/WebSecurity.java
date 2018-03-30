package com.xg.configuration.security;

import com.xg.configuration.security.service.AjaxAwareLoginUrlAuthenticationEntryPoint;
import com.xg.configuration.security.service.AjaxAwareSimpleUrlAuthenticationFailureHandler;
import com.xg.configuration.security.service.AjaxAwareSimpleUrlAuthenticationSuccessHandler;
import com.xg.configuration.security.service.AjaxAwareUsernamePasswordAuthenticationFilter;
import com.xg.configuration.security.service.SecurityInterceptorFilter;
import com.xg.configuration.security.service.UserDetailService;
import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

  @Resource private AjaxAwareLoginUrlAuthenticationEntryPoint ajaxAwareLoginUrlAuthenticationEntryPoint;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.exceptionHandling()
        .authenticationEntryPoint(ajaxAwareLoginUrlAuthenticationEntryPoint)
        .and()
        .authorizeRequests()
        .anyRequest()
        .permitAll() // 所有请求通过
        .and()
        .formLogin()
        .and()
        .csrf()
        .disable();
    http.addFilterBefore(securityInterceptorFilter, FilterSecurityInterceptor.class);
    http.addFilterAt(
        ajaxAwareUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailService).passwordEncoder(bCryptPasswordEncoder);
  }

  @Bean
  AjaxAwareUsernamePasswordAuthenticationFilter ajaxAwareUsernamePasswordAuthenticationFilter()
      throws Exception {
    AjaxAwareUsernamePasswordAuthenticationFilter filter =
        new AjaxAwareUsernamePasswordAuthenticationFilter();
    filter.setAuthenticationSuccessHandler(new AjaxAwareSimpleUrlAuthenticationSuccessHandler());
    filter.setAuthenticationFailureHandler(new AjaxAwareSimpleUrlAuthenticationFailureHandler());
    filter.setFilterProcessesUrl(SecurityConfiguration.LOGIN_URI);
    // 这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
    filter.setAuthenticationManager(authenticationManagerBean());
    return filter;
  }
}
