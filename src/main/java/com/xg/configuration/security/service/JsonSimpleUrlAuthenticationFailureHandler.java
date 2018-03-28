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

/**
 * 支持json
 *
 * @author guzhen
 * @date 2018/3/28
 */
public class JsonSimpleUrlAuthenticationFailureHandler
    extends SimpleUrlAuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
      throws IOException, ServletException {
    if (MediaType.APPLICATION_JSON_VALUE.equals(request.getHeader(HttpHeaders.CONTENT_TYPE))
        || MediaType.APPLICATION_JSON_UTF8_VALUE.equals(
            request.getHeader(HttpHeaders.CONTENT_TYPE))) {
      response.setHeader(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE);
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
        /*
         * USED if you want to AVOID redirect to LoginSuccessful.htm in JSON authentication
         */
      response.getWriter().print("{\"responseCode\":\"Failure\"}");
      response.getWriter().flush();
    } else {
      // TODO Auto-generated method stub
      super.onAuthenticationFailure(request, response, exception);
    }
  }
}
