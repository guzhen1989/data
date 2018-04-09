package com.xg.quartz;

import java.util.Date;
import lombok.Data;

/**
 * 任务信息
 *
 * @author guzhen
 * @date 2018/4/9
 */
@Data
public class TaskInfo {
  private String jobName;
  private String jobGroup;
  private String jobDescription;
  private String jobStatus;
  private String cronExpression;
  private String createTime;

  private Date previousFireTime;
  private Date nextFireTime;
}
