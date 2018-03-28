package com.xg.init;

import com.xg.api.model.uc.Permission;
import com.xg.api.model.uc.Role;
import com.xg.api.model.uc.User;
import com.xg.api.model.uc.UserRoleRef;
import com.xg.repository.PermissionRepository;
import com.xg.repository.RoleRepository;
import com.xg.repository.UserRepository;
import com.xg.repository.UserRoleRefRepository;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 初始化
 *
 * @author guzhen
 * @date 2018/3/27
 */
@Component
@Order
public class InitUser {

  @Resource private UserRepository userRepository;
  @Resource private RoleRepository roleRepository;
  @Resource private PermissionRepository permissionRepository;
  @Resource private UserRoleRefRepository userRoleRefRepository;

  @Resource private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Transactional
  public void initUser() {

    Role role = roleRepository.findByName("管理员");
    if (role == null) {
      role = new Role();
      role.setName("管理员");
      role.setCode("admin");
      roleRepository.save(role);
    }

    List<Permission> all = permissionRepository.findAll();
    Role finalRole = role;
    all.forEach(
        (permission -> {
          permission.setRole(finalRole);
        }));
    permissionRepository.saveAll(all);

    User admin = userRepository.findByUsername("admin");
    if (admin == null) {
      admin = new User();
      admin.setUsername("admin");
      admin.setPassword(bCryptPasswordEncoder.encode("123456"));
      userRepository.save(admin);
    }

    UserRoleRef userRoleRef=userRoleRefRepository.findByUserAndRole(admin,role);
    if(userRoleRef==null){
      userRoleRef=new UserRoleRef();
      userRoleRef.setRole(role);
      userRoleRef.setUser(admin);
      userRoleRefRepository.save(userRoleRef);
    }

  }
}
