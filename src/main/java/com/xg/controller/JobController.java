package com.xg.controller;

import com.xg.controller.vo.JobVo;
import com.xg.quartz.JobService;
import com.xg.quartz.TaskInfo;
import javax.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 任务
 *
 * @author guzhen
 * @date 2018/4/9
 */
@RestController("/task/jobs")
public class JobController {
@Resource
  private JobService jobService;

  /**
   * 添加
   * @param jobVo 任务信息
   */
  @PostMapping
  public ResponseEntity addJob(@RequestBody @Validated JobVo jobVo){
    jobService.addJob(jobVo.getJobName(),jobVo.getJobGroup(),jobVo.getCronExpression(),jobVo.getJobDescription());
    return ResponseEntity.ok().build();
  }

  /**
   * 删除
   *
   * @param jobName 任务名
   * @param jobGroup 任务组
   */
  @DeleteMapping("/{jobGroup}/{jobName}")
  public ResponseEntity delete(@PathVariable String jobName,@PathVariable String jobGroup){
    jobService.delete(jobName,jobGroup);
    return ResponseEntity.ok().build();
  }

  /**
   * 修改
   *
   * @param jobVo 任务信息
   */
  @PutMapping
  public ResponseEntity edit(@RequestBody @Validated JobVo jobVo){
    jobService.edit(jobVo.getJobName(),jobVo.getJobGroup(),jobVo.getCronExpression(),jobVo.getJobDescription());
    return ResponseEntity.ok().build();
  }

  /**
   * 分页查询
   *
   * @param pageable 分页
   * @return 任务信息
   */
  @GetMapping
  public ResponseEntity<Page<TaskInfo>> list(Pageable pageable){
    Page<TaskInfo> page=jobService.list(pageable);
    return ResponseEntity.ok(page);
  }

  /**
   * 暂停
   *
   * @param jobName 任务名
   * @param jobGroup 任务组
   */
  @PutMapping("/{jobGroup}/{jobName}/pause")
  public ResponseEntity pause(@PathVariable String jobName,@PathVariable String jobGroup){
    return ResponseEntity.ok().build();
  }

  /**
   * 重启
   *
   * @param jobName 任务名
   * @param jobGroup 任务组
   */
  @PutMapping("/{jobGroup}/{jobName}/resume")
  public ResponseEntity resume(@PathVariable String jobName,@PathVariable String jobGroup){
    return ResponseEntity.ok().build();
  }

  /**
   * 立即执行
   *
   * @param jobName 任务名
   * @param jobGroup 任务组
   */
  @PutMapping("/{jobGroup}/{jobName}/trigger")
  public ResponseEntity trigger(@PathVariable String jobName,@PathVariable String jobGroup){
    return ResponseEntity.ok().build();
  }
}
