package com.xg.common;

/**
 * 业务异常
 *
 * @author guzhen
 * @date 2018/4/9
 */
public class BusinessException extends RuntimeException{

  public BusinessException() {
    super();
  }

  public BusinessException(String message) {
    super(message);
  }

  public BusinessException(String message, Throwable cause) {
    super(message, cause);
  }

  public BusinessException(Throwable cause) {
    super(cause);
  }

  protected BusinessException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
