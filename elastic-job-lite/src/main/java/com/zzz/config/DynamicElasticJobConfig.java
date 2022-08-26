package com.zzz.config;

import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.zaxxer.hikari.HikariDataSource;
import com.zzz.listener.ElasticJobListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Elastic Job 动态定时任务配置
 * @author songfayuan
 * @date 2021/4/26 9:23 上午
 */
@Configuration
public class DynamicElasticJobConfig {
    @Value("${spring.elasticjob.zookeeper.server-lists}")
    private String serverLists;
    @Value("${spring.elasticjob.zookeeper.namespace}")
    private String namespace;
    @Resource
    private HikariDataSource dataSource;

    @Bean
    public ZookeeperConfiguration zookeeperConfiguration() {
        return new ZookeeperConfiguration(serverLists, namespace);
    }

    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter zookeeperRegistryCenter(ZookeeperConfiguration zookeeperConfiguration){
        return new ZookeeperRegistryCenter(zookeeperConfiguration);
    }

    @Bean
    public ElasticJobListener elasticJobListener(){
        return new ElasticJobListener(100, 100);
    }

    /**
     * 将作业运行的痕迹进行持久化到DB
     *
     * @return
     */
    @Bean
    public JobEventConfiguration jobEventConfiguration() {
        return new JobEventRdbConfiguration(dataSource);
    }

}

