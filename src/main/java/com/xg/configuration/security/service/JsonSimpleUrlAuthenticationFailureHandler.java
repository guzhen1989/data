package com.xg.configuration.security.service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.ELRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * 支持json
 *
 * @author guzhen
 * @date 2018/3/28
 */
public class JsonSimpleUrlAuthenticationFailureHandler
    extends SimpleUrlAuthenticationFailureHandler implements AjaxAware{

  @Override
  public void onAuthenticationFailure(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
      throws IOException, ServletException {
    if (isRestRequest(request)) {
      if(isPreflight(request)){
        response.sendError(HttpServletResponse.SC_NO_CONTENT);
      }else {
        response.setHeader(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.sendError(HttpStatus.UNAUTHORIZED.value(),"{\"responseCode\":\"Failure\"}");
      }
    } else {
        super.onAuthenticationFailure(request, response, exception);
    }
  }

}
