package com.xg.quartz;

/**
 * 定时任务服务
 *
 * @author guzhen
 * @date 2018/4/9
 */
import com.xg.common.BusinessException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JobServiceImpl implements JobService {

  @Resource private Scheduler scheduler;

  /**
   * 添加
   *
   * @param jobName 任务名
   * @param jobGroup 任务组
   * @param cronExpression cron表达式
   * @param jobDescription 描述
   */
  @Override
  public void addJob(
      String jobName, String jobGroup, String cronExpression, String jobDescription) {
    if (StringUtils.isAnyBlank(jobName, jobGroup, cronExpression, jobDescription)) {
      throw new BusinessException(
          String.format("参数错误, jobName={},jobGroup={},cronExpression={},jobDescription={}",
              new Object[]{jobName, jobGroup, cronExpression, jobDescription}));
    }
    String createTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
    try {
      log.info(
          "添加jobName={},jobGroup={},cronExpression={},jobDescription={}",
          jobName,
          jobGroup,
          cronExpression,
          jobDescription);

      if (checkExists(jobName, jobGroup)) {
        log.error("Job已经存在, jobName={},jobGroup={}", jobName, jobGroup);
        throw new BusinessException(
            String.format("Job已经存在, jobName={},jobGroup={}", new Object[]{jobName, jobGroup}));
      }

      TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
      JobKey jobKey = JobKey.jobKey(jobName, jobGroup);

      CronScheduleBuilder schedBuilder =
          CronScheduleBuilder.cronSchedule(cronExpression)
              .withMisfireHandlingInstructionDoNothing();
      CronTrigger trigger =
          TriggerBuilder.newTrigger()
              .withIdentity(triggerKey)
              .withDescription(createTime)
              .withSchedule(schedBuilder)
              .build();

      Class<? extends Job> clazz = (Class<? extends Job>) Class.forName(jobName);
      JobDetail jobDetail =
          JobBuilder.newJob(clazz).withIdentity(jobKey).withDescription(jobDescription).build();
      scheduler.scheduleJob(jobDetail, trigger);
    } catch (SchedulerException | ClassNotFoundException e) {
      log.error("添加job失败, jobName={},jobGroup={},e={}", jobName, jobGroup, e);
      throw new BusinessException("类名不存在或执行表达式错误");
    }
  }

  /**
   * 验证是否存在
   *
   * @param jobName 任务名
   * @param jobGroup 分组
   * @throws SchedulerException 调度异常
   */
  private boolean checkExists(String jobName, String jobGroup) throws SchedulerException {
    TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
    return scheduler.checkExists(triggerKey);
  }

  /**
   * 删除
   *
   * @param jobName 任务名
   * @param jobGroup 任务组
   */
  @Override
  public void delete(String jobName, String jobGroup) {
    try {
      log.info("删除jobName={},jobGroup={}", jobName, jobGroup);
      TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
      if (checkExists(jobName, jobGroup)) {
        scheduler.pauseTrigger(triggerKey);
        scheduler.unscheduleJob(triggerKey);
      }
    } catch (SchedulerException e) {
      log.error("删除job失败, jobName={},jobGroup={},e={}", jobName, jobGroup, e);
      throw new BusinessException(e.getMessage());
    }
  }

  /**
   * 修改
   *
   * @param jobName 任务名
   * @param jobGroup 任务组
   * @param cronExpression cron表达式
   * @param jobDescription 描述
   */
  @Override
  public void edit(String jobName, String jobGroup, String cronExpression, String jobDescription) {
    if (StringUtils.isAnyBlank(jobName, jobGroup, cronExpression, jobDescription)) {
      throw new BusinessException(
          String.format("参数错误, jobName={},jobGroup={},cronExpression={},jobDescription={}",
              new Object[]{jobName, jobGroup, cronExpression, jobDescription}));
    }
    String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    try {
      log.info(
          "修改jobName={},jobGroup={},cronExpression={},jobDescription={}",
          jobName,
          jobGroup,
          cronExpression,
          jobDescription);
      if (!checkExists(jobName, jobGroup)) {
        log.error("Job不存在, jobName={},jobGroup={}", jobName, jobGroup);
        throw new BusinessException(
            String.format("Job不存在, jobName={},jobGroup={}", new Object[]{jobName, jobGroup}));
      }
      TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
      JobKey jobKey = new JobKey(jobName, jobGroup);
      CronScheduleBuilder cronScheduleBuilder =
          CronScheduleBuilder.cronSchedule(cronExpression)
              .withMisfireHandlingInstructionDoNothing();
      CronTrigger cronTrigger =
          TriggerBuilder.newTrigger()
              .withIdentity(triggerKey)
              .withDescription(createTime)
              .withSchedule(cronScheduleBuilder)
              .build();

      JobDetail jobDetail = scheduler.getJobDetail(jobKey);
      jobDetail.getJobBuilder().withDescription(jobDescription);
      HashSet<Trigger> triggerSet = new HashSet<>();
      triggerSet.add(cronTrigger);

      scheduler.scheduleJob(jobDetail, triggerSet, true);
    } catch (SchedulerException e) {
      log.error("修改job失败, jobName={},jobGroup={},e={}", jobName, jobGroup, e);
      throw new BusinessException("类名不存在或执行表达式错误");
    }
  }

  /**
   * 分页查询
   *
   * @param pageable 分页
   * @return 任务信息
   */
  @Override
  public Page<TaskInfo> list(Pageable pageable) {
    int total = 0;
    List<TaskInfo> list = new ArrayList<>();
    try {
      for (String groupJob : scheduler.getJobGroupNames()) {
        for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.<JobKey>groupEquals(groupJob))) {
          List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
          for (Trigger trigger : triggers) {
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);

            String cronExpression = "", createTime = "";

            if (trigger instanceof CronTrigger) {
              CronTrigger cronTrigger = (CronTrigger) trigger;
              cronExpression = cronTrigger.getCronExpression();
              createTime = cronTrigger.getDescription();
            }
            TaskInfo info = new TaskInfo();
            info.setJobName(jobKey.getName());
            info.setJobGroup(jobKey.getGroup());
            info.setJobDescription(jobDetail.getDescription());
            info.setJobStatus(triggerState.name());
            info.setCronExpression(cronExpression);
            info.setCreateTime(createTime);
            info.setPreviousFireTime(trigger.getPreviousFireTime());
            info.setNextFireTime(trigger.getNextFireTime());
            list.add(info);
          }
        }
      }
    } catch (SchedulerException e) {
      log.error(
          "分页查询定时任务失败，page={},size={},e={}", pageable.getPageNumber(), pageable.getPageSize(), e);
    }
    total = list.size();
    list =
        list.stream()
            .skip(pageable.getOffset())
            .limit(pageable.getPageSize())
            .collect(Collectors.toList());
    return new PageImpl<>(list, pageable, total);
  }

  /**
   * 暂停
   *
   * @param jobName 任务名
   * @param jobGroup 任务组
   */
  @Override
  public void pause(String jobName, String jobGroup) {
    try {
      log.info("暂停jobName={},jobGroup={}", jobName, jobGroup);
      TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
      if (!checkExists(jobName, jobGroup)) {
        log.error("Job不存在, jobName={},jobGroup={}", jobName, jobGroup);
        throw new BusinessException(
            String.format("Job不存在, jobName={},jobGroup={}", new Object[]{jobName, jobGroup}));
      }
      scheduler.pauseTrigger(triggerKey);
    } catch (SchedulerException e) {
      log.error("暂停job失败, jobName={},jobGroup={},e={}", jobName, jobGroup, e);
      throw new BusinessException(e.getMessage());
    }
  }

  /**
   * 重启
   *
   * @param jobName 任务名
   * @param jobGroup 任务组
   */
  @Override
  public void resume(String jobName, String jobGroup) {
    try {
      log.info("重启jobName={},jobGroup={}", jobName, jobGroup);
      TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
      if (!checkExists(jobName, jobGroup)) {
        log.error("Job不存在, jobName={},jobGroup={}", jobName, jobGroup);
        throw new BusinessException(
            String.format("Job不存在, jobName={},jobGroup={}", new Object[]{jobName, jobGroup}));
      }
      scheduler.resumeTrigger(triggerKey);
    } catch (SchedulerException e) {
      log.error("重启job失败, jobName={},jobGroup={},e={}", jobName, jobGroup, e);
      throw new BusinessException(e.getMessage());
    }
  }

  /**
   * 立即执行
   *
   * @param jobName 任务名
   * @param jobGroup 任务组
   */
  @Override
  public void trigger(String jobName, String jobGroup) {
    try {
      log.info("立即执行jobName={},jobGroup={}", jobName, jobGroup);
      if (!checkExists(jobName, jobGroup)) {
        log.error("Job不存在, jobName={},jobGroup={}", jobName, jobGroup);
        throw new BusinessException(
            String.format("Job不存在, jobName={},jobGroup={}", new Object[]{jobName, jobGroup}));
      }
      JobKey jobKey = new JobKey(jobName, jobGroup);
      scheduler.triggerJob(jobKey);
    } catch (SchedulerException e) {
      log.error("立即执行job失败, jobName={},jobGroup={},e={}", jobName, jobGroup, e);
      throw new BusinessException(e.getMessage());
    }
  }
}
