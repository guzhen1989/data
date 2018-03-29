package com.xg.configuration.security.service;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.ELRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * ${DESCRIPTION}
 *
 * @author guzhen
 * @date 2018/3/29
 */
public interface AjaxAware {
  RequestMatcher REST_REQUEST_MATCHER = new ELRequestMatcher(
      "hasHeader('X-Requested-With','XMLHttpRequest')");

  default boolean isPreflight(HttpServletRequest request) {
    return "OPTIONS".equals(request.getMethod());
  }

  default boolean isRestRequest(HttpServletRequest request) {
    return REST_REQUEST_MATCHER.matches(request);
  }
}
