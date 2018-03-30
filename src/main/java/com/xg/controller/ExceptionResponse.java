package com.xg.controller;

import java.io.Serializable;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

/**
 * 异常返回
 *
 * @author guzhen
 * @date 2018/3/30
 */
public class ExceptionResponse<T> implements Serializable {
  private Date timestamp;
  private Integer status;
  private T error;
  private String message;
  private String path;

  {
    timestamp = new Date();
  }

  public ExceptionResponse(ErrorCode errorCode, HttpServletRequest request) {
    this.status = errorCode.getCode();
    this.message = errorCode.getMessage();
    this.path = request.getRequestURI();
  }

  public T getError() {
    return error;
  }

  public void setError(T error) {
    this.error = error;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }
}
