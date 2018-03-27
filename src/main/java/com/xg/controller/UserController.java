package com.xg.controller;

import com.xg.annotation.Perm;
import com.xg.annotation.Perms;
import com.xg.annotation.Res;
import com.xg.api.model.uc.User;
import com.xg.repository.UserRepository;
import java.util.Optional;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户
 *
 * @author guzhen
 * @date 2018/3/19
 */
@RestController
@RequestMapping("/uc/users")
public class UserController {
  @Resource private UserRepository userRepository;

  @PostMapping()
  public User addUser(@RequestBody User user) {
    userRepository.save(user);
    return user;
  }
  @GetMapping()
  public User getUser(@RequestBody User user) {
    userRepository.findByUsername(user.getUsername());
    return user;
  }

  @Perms({@Perm(name = "权限一")})
  @Res("测试")
  @GetMapping("/test/{id}")
  public User test(@PathVariable("id") Integer id) {
    Optional<User> byId = userRepository.findById(id);
    return byId.get();
  }
}
