package com.xg.controller;

import com.xg.api.model.uc.User;
import com.xg.repository.UserRepository;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户
 *
 * @author guzhen
 * @date 2018/3/19
 */
@RestController("/uc/user")
public class UserController {
  @Resource private UserRepository userRepository;

  @PostMapping()
  public User addUser(@RequestBody User user) {
    userRepository.save(user);
    return user;
  }
}
