package com.xg.configuration.security.service;

import javax.annotation.Resource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Service;

/**
 * 安全拦截器
 *
 * @author guzhen
 * @date 2018/3/19
 */
@Service
public class SecurityInterceptorFilter extends FilterSecurityInterceptor implements
    InitializingBean {

  @Resource private DaoSecurityMetadataSource daoSecurityMetadataSource;

  public SecurityInterceptorFilter(@Autowired UrprAccessDecisionManager urprAccessDecisionManager){
    setAccessDecisionManager(urprAccessDecisionManager);
  }

  @Override
  public SecurityMetadataSource obtainSecurityMetadataSource() {
    return daoSecurityMetadataSource;
  }
}
