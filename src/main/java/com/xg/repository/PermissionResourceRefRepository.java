package com.xg.repository;

import com.xg.api.model.uc.Permission;
import com.xg.api.model.uc.PermissionResourceRef;
import com.xg.api.model.uc.Resource;
import com.xg.api.model.uc.RolePermissionRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

/**
 * @author guzhen
 * @date 2018/3/19
 */
@Repository
public interface PermissionResourceRefRepository extends JpaRepository<PermissionResourceRef, Integer> {
  PermissionResourceRef findByPermissionAndResourceUrlAndResourceMethod(Permission permission,String url,HttpMethod httpMethod);
}
