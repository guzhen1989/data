package com.xg.quartz;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * ${DESCRIPTION}
 *
 * @author guzhen
 * @date 2018/4/9
 */
public interface JobService {

  /**
   * 添加
   *
   * @param jobName 任务名
   * @param jobGroup 任务组
   * @param cronExpression cron表达式
   * @param jobDescription 描述
   */
  void addJob(
      String jobName, String jobGroup, String cronExpression, String jobDescription);

  /**
   * 删除
   *
   * @param jobName 任务名
   * @param jobGroup 任务组
   */
  void delete(String jobName, String jobGroup);

  /**
   * 修改
   *
   * @param jobName 任务名
   * @param jobGroup 任务组
   * @param cronExpression cron表达式
   * @param jobDescription 描述
   */
  void edit(String jobName, String jobGroup, String cronExpression, String jobDescription);

  /**
   * 分页查询
   *
   * @param pageable 分页
   * @return 任务信息
   */
  Page<TaskInfo> list(Pageable pageable);

  /**
   * 暂停
   *
   * @param jobName 任务名
   * @param jobGroup 任务组
   */
  void pause(String jobName, String jobGroup);

  /**
   * 重启
   *
   * @param jobName 任务名
   * @param jobGroup 任务组
   */
  void resume(String jobName, String jobGroup);

  /**
   * 立即执行
   *
   * @param jobName 任务名
   * @param jobGroup 任务组
   */
  void trigger(String jobName, String jobGroup);
}
