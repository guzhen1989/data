package com.xg.controller;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Rest 基础Controller
 *
 * @author guzhen
 * @date 2018/3/30
 */
@RestControllerAdvice
public class RestExceptionAdvice {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionResponse methodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException,HttpServletRequest httpServletRequest){
    ExceptionResponse exceptionResponse=new ExceptionResponse(ErrorCode.BAD_PARAM,httpServletRequest);
    BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
    List<ObjectError> allErrors = bindingResult.getAllErrors();
//    bindingResult.getModel()
//    httpServletRequest.getRequestURI()
//
//    ResponseEntity responseEntity=new ResponseEntity()
    return exceptionResponse;
  }
  @ExceptionHandler(BindException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionResponse methodArgumentNotValidException(BindException bindException,HttpServletRequest httpServletRequest){
    ExceptionResponse exceptionResponse=new ExceptionResponse(ErrorCode.BAD_PARAM,httpServletRequest);
    BindingResult bindingResult = bindException.getBindingResult();
    List<ObjectError> allErrors = bindingResult.getAllErrors();
//    bindingResult.getModel()
//    httpServletRequest.getRequestURI()
//
//    ResponseEntity responseEntity=new ResponseEntity()
    return exceptionResponse;
  }

}
