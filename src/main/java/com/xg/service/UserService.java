package com.xg.service;

import com.xg.api.model.uc.User;
import com.xg.controller.vo.UserVo;
import com.xg.repository.UserRepository;
import javax.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户处理
 *
 * @author guzhen
 * @date 2018/3/30
 */
@Service
@Transactional
public class UserService {

  @Resource private UserRepository userRepository;

  public void addUser(UserVo userVo){
    User user = userRepository.findByUsername(userVo.getUsername());
    if(user==null){
      user=userVo.convert();
      userRepository.save(user);
    }
  }

  public Page<User> getUsers(Pageable pageable){
    return userRepository.findAll(pageable);
  }
}
