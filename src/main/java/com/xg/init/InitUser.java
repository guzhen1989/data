package com.xg.init;

import com.xg.api.model.uc.Permission;
import com.xg.api.model.uc.Role;
import com.xg.api.model.uc.RolePermissionRef;
import com.xg.api.model.uc.User;
import com.xg.api.model.uc.UserRoleRef;
import com.xg.configuration.security.service.DaoSecurityMetadataSource;
import com.xg.repository.PermissionRepository;
import com.xg.repository.RolePermissionRefRepository;
import com.xg.repository.RoleRepository;
import com.xg.repository.UserRepository;
import com.xg.repository.UserRoleRefRepository;
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
  @Resource private RolePermissionRefRepository rolePermissionRefRepository;

  @Resource private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Resource private DaoSecurityMetadataSource daoSecurityMetadataSource;

  @Transactional
  public void initUser() {

    //初始化角色
    Role role = roleRepository.findByName("管理员");
    if (role == null) {
      role = new Role();
      role.setName("管理员");
      role.setCode("admin");
      roleRepository.save(role);
    }

    //初始化角色权限关系
    List<Permission> all = permissionRepository.findAll();
    Role finalRole = role;
    all.forEach(
        (permission -> {
          RolePermissionRef roleAndPermission =
              rolePermissionRefRepository.findByRoleAndPermission(finalRole, permission);
          if (roleAndPermission == null) {
            roleAndPermission = new RolePermissionRef();
            roleAndPermission.setPermission(permission);
            roleAndPermission.setRole(finalRole);
            rolePermissionRefRepository.save(roleAndPermission);
          }
        }));
    permissionRepository.saveAll(all);

    //初始化用户
    User admin = userRepository.findByUsername("admin");
    if (admin == null) {
      admin = new User();
      admin.setUsername("admin");
      admin.setPassword(bCryptPasswordEncoder.encode("123456"));
      userRepository.save(admin);
    }

    //初始化用户角色关系
    UserRoleRef userRoleRef = userRoleRefRepository.findByUserAndRole(admin, role);
    if (userRoleRef == null) {
      userRoleRef = new UserRoleRef();
      userRoleRef.setRole(role);
      userRoleRef.setUser(admin);
      userRoleRefRepository.save(userRoleRef);
    }

    daoSecurityMetadataSource.reloadResourceDefine();
  }
}
