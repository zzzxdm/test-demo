package com.zzz.handler;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.internal.schedule.JobRegistry;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.zzz.entity.JobDynamicTask;
import com.zzz.service.ElasticJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 动态定时任务相关操作工具
 *
 * @author songfayuan
 * @date 2021/4/26 10:54 上午
 */
@Slf4j
@Component
public class ElasticJobHandler {

    @Resource
    private ZookeeperRegistryCenter zookeeperRegistryCenter;
    @Resource
    private JobEventConfiguration jobEventConfiguration;
    @Resource
    private ElasticJobListener elasticJobListener;
    @Resource
    ElasticJobService elasticJobService;

    /***
     * 动态创建定时任务
     * @param jobName:定时任务名称
     * @param cron:表达式
     * @param shardingTotalCount:分片数量
     * @param instance:定时任务实例
     * @param parameters:参数
     * @param description:作业描述
     */
    public void addJob(String jobName, String cron, int shardingTotalCount, SimpleJob instance, String parameters, String description) {
        log.info("动态创建定时任务：jobName = {}, cron = {}, shardingTotalCount = {}, parameters = {}", jobName, cron, shardingTotalCount, parameters);
        LiteJobConfiguration.Builder builder = LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(
                JobCoreConfiguration.newBuilder(
                        jobName,
                        cron,
                        shardingTotalCount
                ).jobParameter(parameters).description(description).build(),
                instance.getClass().getName()
        )).overwrite(true);
        LiteJobConfiguration liteJobConfiguration = builder.build();
        new SpringJobScheduler(instance, zookeeperRegistryCenter, liteJobConfiguration, jobEventConfiguration, elasticJobListener).init();
    }

    /**
     * 更新定时任务
     *
     * @param jobName
     * @param cron
     */
    public void updateJob(String jobName, String cron) {
        log.info("更新定时任务：jobName = {}, cron = {}", jobName, cron);
        JobRegistry.getInstance().getJobScheduleController(jobName).rescheduleJob(cron);
        JobDynamicTask dynamicTask = elasticJobService.getByJobName(jobName);
        if (dynamicTask != null) {
            dynamicTask.setCron(cron);
            elasticJobService.saveOrUpdate(dynamicTask);
        }
    }

    /**
     * 删除定时任务
     *
     * @param jobName
     */
    public void removeJob(String jobName) {
        log.info("删除定时任务：jobName = {}", jobName);
        JobRegistry.getInstance().getJobScheduleController(jobName).shutdown();
        elasticJobService.removeByJobName(jobName);
    }


    /**
     * 取消定时任务
     *
     * @param jobName
     */
    public void pauseJob(String jobName) {
        log.info("暂停定时任务：jobName = {}", jobName);
        JobRegistry.getInstance().getJobScheduleController(jobName).pauseJob();
    }

}


