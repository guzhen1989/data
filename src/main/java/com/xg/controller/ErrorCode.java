package com.xg.controller;

/**
 * 错误码
 *
 * @author guzhen
 * @date 2018/3/30
 */
public enum ErrorCode {

  /**
   * 参数错误
   */
  BAD_PARAM(400,"参数错误"),

  /**
   * 未知错误
   */
  SYSTEM_ERROR(500,"系统错误"),

  //业务错误 1000以上
  ;

  private Integer code;
  private String message;

  ErrorCode(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public Integer getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
