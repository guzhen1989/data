package com.xg.controller.vo;

import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 任务
 *
 * @author guzhen
 * @date 2018/4/9
 */
@Data
public class JobVo {
  @NotNull
  private String jobName;
  @NotNull
  private String jobGroup;
  @NotNull
  private String cronExpression;
  @NotNull
  private String jobDescription;
}
