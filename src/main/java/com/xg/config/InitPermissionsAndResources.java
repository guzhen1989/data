package com.xg.config;

import com.xg.annotation.Perm;
import com.xg.annotation.Perms;
import com.xg.annotation.Res;
import com.xg.api.model.uc.Permission;
import com.xg.repository.PermissionRepository;
import com.xg.repository.ResourceRepository;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * 初始化资源和权限
 *
 * @author guzhen
 * @date 2018/3/27
 */
@Component
public class InitPermissionsAndResources {

  @Resource private RequestMappingHandlerMapping requestMappingHandlerMapping;

  @Resource private ResourceRepository resourceRepository;

  @Resource private PermissionRepository permissionRepository;

  private Permission getPermission(String name) {
    Permission permission = permissionRepository.findByName(name);
    if (permission == null) {
      permission = new Permission();
      permission.setName(name);
    }
    permissionRepository.save(permission);
    return permission;
  }

  @PostConstruct
  public void init() {
    Map<RequestMappingInfo, HandlerMethod> handlerMethods =
        requestMappingHandlerMapping.getHandlerMethods();
    initResources(handlerMethods);
    initPermissions(handlerMethods);
  }

  private void initPermissions(Map<RequestMappingInfo, HandlerMethod> handlerMethods) {
    handlerMethods.forEach(
        (requestMappingInfo, handlerMethod) -> {
          Method method = handlerMethod.getMethod();
          if (method.isAnnotationPresent(Perms.class) && method.isAnnotationPresent(Res.class)) {
            Perms perms = method.getAnnotation(Perms.class);
            for (Perm perm : perms.value()) {
              Permission permission = getPermission(perm.name());
              if (StringUtils.isNotEmpty(perm.parent())) {
                Permission parent = getPermission(perm.parent());
                permission.setPermission(parent);
              }
              requestMappingInfo
                  .getPatternsCondition()
                  .getPatterns()
                  .forEach(
                      (pattern) -> {
                        requestMappingInfo
                            .getMethodsCondition()
                            .getMethods()
                            .forEach(
                                (requestMethod) -> {
                                  com.xg.api.model.uc.Resource resource =
                                      resourceRepository.findByUrlAndMethod(
                                          pattern, HttpMethod.resolve(requestMethod.name()));
                                  List<com.xg.api.model.uc.Resource> list =
                                      permission.getResources();
                                  if (list == null) {
                                    list = new ArrayList<>();
                                  }
                                  list.add(resource);
                                  permission.setResources(list);
                                });
                      });
              permissionRepository.save(permission);
            }
          }
        });
  }

  private void initResources(Map<RequestMappingInfo, HandlerMethod> handlerMethods) {
    List<com.xg.api.model.uc.Resource> list = new ArrayList<>();
    handlerMethods.forEach(
        (requestMappingInfo, handlerMethod) -> {
          if (handlerMethod.getMethod().isAnnotationPresent(Res.class)) {
            requestMappingInfo
                .getPatternsCondition()
                .getPatterns()
                .forEach(
                    (pattern) -> {
                      requestMappingInfo
                          .getMethodsCondition()
                          .getMethods()
                          .forEach(
                              (requestMethod) -> {
                                com.xg.api.model.uc.Resource resource =
                                    resourceRepository.findByUrlAndMethod(
                                        pattern, HttpMethod.resolve(requestMethod.name()));
                                if (resource == null) {
                                  resource = new com.xg.api.model.uc.Resource();
                                  resource.setMethod(HttpMethod.resolve(requestMethod.name()));
                                  resource.setUrl(pattern);
                                  resource.setName(
                                      handlerMethod.getMethod().getAnnotation(Res.class).value());
                                } else {
                                  resource.setName(
                                      handlerMethod.getMethod().getAnnotation(Res.class).value());
                                }
                                list.add(resource);
                              });
                    });
          }
        });
    if (list.size() > 0) {
      resourceRepository.saveAll(list);
    }
  }
}
