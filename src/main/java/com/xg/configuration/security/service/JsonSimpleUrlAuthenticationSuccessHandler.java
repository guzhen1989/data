package com.xg.configuration.security.service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.ELRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * 支持json
 *
 * @author guzhen
 * @date 2018/3/28
 */
public class JsonSimpleUrlAuthenticationSuccessHandler extends
    SimpleUrlAuthenticationSuccessHandler implements AjaxAware{

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    if (isRestRequest(request)) {
      response.setHeader(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE);
      response.getWriter().print("{\"responseCode\":\"SUCCESS\"}");
      response.getWriter().flush();
    } else {
      super.onAuthenticationSuccess(request, response, authentication);
    }
  }
}
