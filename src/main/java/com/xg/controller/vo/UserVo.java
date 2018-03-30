package com.xg.controller.vo;

import com.xg.api.model.uc.User;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 用户
 *
 * @author guzhen
 * @date 2018/3/30
 */
public class UserVo {
  @Length(min = 5,max = 64)
  private String username;
  @Length(min = 5,max = 64)
  private String password;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public User convert(){
    User user=new User();
    user.setUsername(getUsername());
    user.setPassword(getPassword());
    return user;
  }
}
