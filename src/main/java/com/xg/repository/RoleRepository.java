package com.xg.repository;

import com.xg.api.model.uc.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author guzhen
 * @date 2018/3/19
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
  Role findByName(String name);
}
