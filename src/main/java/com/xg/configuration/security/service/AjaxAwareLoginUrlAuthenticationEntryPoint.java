package com.xg.configuration.security.service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.ELRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * 支持ajax
 *
 * @author guzhen
 * @date 2018/3/29
 */
public class AjaxAwareLoginUrlAuthenticationEntryPoint extends
    LoginUrlAuthenticationEntryPoint implements AjaxAware {


  public AjaxAwareLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
    super(loginFormUrl);
  }

  @Override
  public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException) throws IOException, ServletException {
    if(isPreflight(request)){
      response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    } else if (isRestRequest(request)) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    } else {
      super.commence(request, response, authException);
    }
  }
}
