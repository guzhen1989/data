package com.xg.repository;

import com.xg.api.model.uc.Permission;
import com.xg.api.model.uc.Role;
import com.xg.api.model.uc.User;
import com.xg.api.model.uc.UserRoleRef;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author guzhen
 * @date 2018/3/19
 */
@Repository
public interface UserRoleRefRepository extends JpaRepository<UserRoleRef, Integer> {

  UserRoleRef findByUserAndRole(User user,Role role);
}
