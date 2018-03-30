package com.xg.configuration.security.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xg.controller.vo.UserVo;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Json登陆
 *
 * @author guzhen
 * @date 2018/3/28
 */
public class AjaxAwareUsernamePasswordAuthenticationFilter
    extends UsernamePasswordAuthenticationFilter implements AjaxAware {
  private static final ThreadLocal<UserVo> LOGIN_INFO = new ThreadLocal<>();
  private final ObjectMapper mapper;

  {
    mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  private boolean checkJson(HttpServletRequest request) {
    try {
      if (isRestRequest(request)) {
        if (LOGIN_INFO.get() == null) {
          UserVo user = mapper.readValue(request.getInputStream(), UserVo.class);
          LOGIN_INFO.set(user);
        }
        return true;
      }
    } catch (IOException e) {
      logger.warn(e.getMessage());
    }
    return false;
  }

  @Override
  protected String obtainPassword(HttpServletRequest request) {
    if (checkJson(request)) {
      UserVo user = LOGIN_INFO.get();
      if (user != null) {
        LOGIN_INFO.remove();
        return user.getPassword();
      }
    }
    return super.obtainPassword(request);
  }

  @Override
  protected String obtainUsername(HttpServletRequest request) {
    if (checkJson(request)) {
      UserVo user = LOGIN_INFO.get();
      if (user != null) {
        return user.getUsername();
      }
    }
    return super.obtainUsername(request);
  }
}
