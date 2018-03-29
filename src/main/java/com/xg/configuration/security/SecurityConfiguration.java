package com.xg.configuration.security;

import com.xg.configuration.security.service.AjaxAwareLoginUrlAuthenticationEntryPoint;
import com.xg.configuration.security.service.JsonUsernamePasswordAuthenticationFilter;
import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 安全配置
 *
 * @author guzhen
 * @date 2018/3/27
 */
@Configuration
public class SecurityConfiguration {

  public static final String LOGIN_URI="/login/self";

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder(){
    return new BCryptPasswordEncoder();
  }
@Bean
  AjaxAwareLoginUrlAuthenticationEntryPoint ajaxAwareLoginUrlAuthenticationEntryPoint(){
    return new AjaxAwareLoginUrlAuthenticationEntryPoint(LOGIN_URI);
  }
}
