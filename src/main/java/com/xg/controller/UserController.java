package com.xg.controller;

import com.xg.annotation.Perm;
import com.xg.annotation.Perms;
import com.xg.annotation.Res;
import com.xg.api.model.uc.User;
import com.xg.controller.vo.UserVo;
import com.xg.repository.UserRepository;
import com.xg.service.UserService;
import java.util.Optional;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
  @Resource private UserService userService;

  @Perms({@Perm(name = "权限一")})
  @Res("新增用户")
  @PostMapping()
  public void addUser(@RequestBody @Validated UserVo user) {
    userService.addUser(user);
  }

  @Perms({@Perm(name = "权限一")})
  @Res("分页查询用户")
  @GetMapping()
  public Page<User> getUsers(Pageable pageable) {
    return userService.getUsers(pageable);
  }

  @Perms({@Perm(name = "权限一")})
  @Res("测试")
  @GetMapping("/test/{id}")
  public User test(@PathVariable("id") Integer id) {
//    Optional<User> byId = userRepository.findById(id);
    return null;
  }
}
