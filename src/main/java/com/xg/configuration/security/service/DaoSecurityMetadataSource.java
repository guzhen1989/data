package com.xg.configuration.security.service;

import com.xg.api.model.uc.Permission;
import com.xg.api.model.uc.PermissionResourceRef;
import com.xg.api.model.uc.Role;
import com.xg.api.model.uc.RolePermissionRef;
import com.xg.repository.RoleRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 * 数据库权限数据
 *
 * @author guzhen
 * @date 2018/3/19
 */
@Service
@Transactional
public class DaoSecurityMetadataSource implements SecurityMetadataSource {

  private static final String HANDLER_MAPPING_INTROSPECTOR_BEAN_NAME =
      "mvcHandlerMappingIntrospector";
  private static HashMap<com.xg.api.model.uc.Resource, Collection<ConfigAttribute>>
      ROLE_RESOURCE_MAP = null;
  @Resource private ApplicationContext applicationContext;
  @Resource private RoleRepository roleRepository;

  // TODO 权限修改是重新加载权限

  @Override
  public Collection<ConfigAttribute> getAllConfigAttributes() {
    if (ROLE_RESOURCE_MAP == null) {
      reloadResourceDefine();
    }
    Collection<ConfigAttribute> configAttributes = new ArrayList<>();
    ROLE_RESOURCE_MAP.values().forEach(configAttributes::addAll);
    return configAttributes;
  }

  // 此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
  @Override
  public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
    if (ROLE_RESOURCE_MAP == null) {
      reloadResourceDefine();
    }
    // object 中包含用户请求的request 信息
    HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
    MvcRequestMatcher matcher;
    HandlerMappingIntrospector introspector =
        this.applicationContext.getBean(
            HANDLER_MAPPING_INTROSPECTOR_BEAN_NAME, HandlerMappingIntrospector.class);
    for (com.xg.api.model.uc.Resource resource : ROLE_RESOURCE_MAP.keySet()) {

      matcher = new MvcRequestMatcher(introspector, resource.getUrl());
      if (resource.getMethod() != null) {
        matcher.setMethod(resource.getMethod());
      }
      if (matcher.matches(request)) {
        return ROLE_RESOURCE_MAP.get(resource);
      }
    }

    return null;
  }

  /** 加载权限表中所有权限 */
  public void reloadResourceDefine() {
    HashMap<com.xg.api.model.uc.Resource, Collection<ConfigAttribute>> map = new HashMap<>();
    List<Role> roles = roleRepository.findAll();
    for (Role role : roles) {
      ConfigAttribute cfg = new SecurityConfig(role.getCode());
      if(CollectionUtils.isEmpty(role.getRolePermissionRefs())){
        continue;
      }
      List<Permission> permissions =
          role.getRolePermissionRefs()
              .stream()
              .map((RolePermissionRef::getPermission))
              .collect(Collectors.toList());
      for (Permission permission : permissions) {
        List<com.xg.api.model.uc.Resource> resources =
            permission
                .getPermissionResourceRefs()
                .stream()
                .map(PermissionResourceRef::getResource)
                .collect(Collectors.toList());
        for (com.xg.api.model.uc.Resource resource : resources) {
          Collection<ConfigAttribute> roleCollection = map.get(resource);
          if (CollectionUtils.isEmpty(roleCollection)) {
            roleCollection = new ArrayList<>();
          }
          roleCollection.add(cfg);
          map.put(resource, roleCollection);
        }
      }
    }
    ROLE_RESOURCE_MAP = map;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }
}
