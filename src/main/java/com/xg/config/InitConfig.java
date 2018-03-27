package com.xg.config;

import com.xg.api.model.uc.Role;
import com.xg.api.model.uc.User;
import com.xg.repository.PermissionRepository;
import com.xg.repository.RoleRepository;
import com.xg.repository.UserRepository;
import java.util.ArrayList;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 初始化
 *
 * @author guzhen
 * @date 2018/3/27
 */
@Component
public class InitConfig {

  @Resource
  private UserRepository userRepository;
  @Resource
  private RoleRepository roleRepository;
  @Resource
  private PermissionRepository  permissionRepository;

  @Resource private BCryptPasswordEncoder bCryptPasswordEncoder;

  @PostConstruct
  public void init(){
    initUser();
  }
  
  private void initUser(){

    Role role=roleRepository.findByName("管理员");
    if(role==null){
      role=new Role();
      role.setName("管理员");
      role.setCode("admin");
    }
    role.setPermissions(permissionRepository.findAll());
    roleRepository.save(role);

    User admin = userRepository.findByUsername("admin");
    if(admin==null){
      admin=new User();
      admin.setUsername("admin");
    }
    admin.setRoles(Arrays.asList(role));
    admin.setPassword(bCryptPasswordEncoder.encode("123456"));
    userRepository.save(admin);
  }
  
}
