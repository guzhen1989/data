package com.xg.configuration.security.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xg.api.model.uc.User;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Json登陆
 *
 * @author guzhen
 * @date 2018/3/28
 */
public class JsonUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private static final ThreadLocal<User> LOGIN_INFO = new ThreadLocal<>();
  private final ObjectMapper mapper;

  {
    mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  private boolean checkJson(HttpServletRequest request) {
    try {
      if (MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType())
          || MediaType.APPLICATION_JSON_UTF8_VALUE.equals(
              request.getContentType())) {
        if(LOGIN_INFO.get()==null) {
          User user = mapper.readValue(request.getInputStream(), User.class);
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
      User user = LOGIN_INFO.get();
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
      User user = LOGIN_INFO.get();
      if (user != null) {
        return user.getUsername();
      }
    }
    return super.obtainUsername(request);
  }
}
