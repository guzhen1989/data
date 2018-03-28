package com.xg.init;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * 初始化
 *
 * @author guzhen
 * @date 2018/3/27
 */
@Component
public class Init {

  @Resource
  private InitPermissionsAndResources initPermissionsAndResources;
  @Resource private InitUser initUser;

  @PostConstruct
  public void init(){
    initPermissionsAndResources.initPermissionsAndResources();

    initUser.initUser();
  }
  

}
