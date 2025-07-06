package com.zzz.springdemo.task;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 定时任务
 *
 * @author wl
 */
@Slf4j
@Data
@Component
@EnableScheduling
@PropertySource("classpath:/task-config.ini")
public class ScheduleTask implements SchedulingConfigurer {

    @Value("${printTime.cron}")
    private String cron;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        log.info("corn is: {}", cron);
        // 动态使用cron表达式设置循环间隔
        taskRegistrar.addTriggerTask(() -> log.info("Current time： {}", LocalDateTime.now()), triggerContext -> {
            // 使用CronTrigger触发器，可动态修改cron表达式来操作循环规则
            CronTrigger cronTrigger = new CronTrigger(cron);
            Date nextExecutionTime = cronTrigger.nextExecutionTime(triggerContext);
            return nextExecutionTime;
        });
    }

    @PostConstruct
    public void init() {
        log.info("config task...");
    }

}