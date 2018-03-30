package com.xg.repository;

import com.xg.api.model.uc.Permission;
import com.xg.api.model.uc.Role;
import com.xg.api.model.uc.RolePermissionRef;
import com.xg.api.model.uc.User;
import com.xg.api.model.uc.UserRoleRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author guzhen
 * @date 2018/3/19
 */
@Repository
public interface RolePermissionRefRepository extends JpaRepository<RolePermissionRef, Integer> {
  RolePermissionRef findByRoleAndPermission(Role role,Permission permission);
}
