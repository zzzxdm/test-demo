package com.zzz.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.zen.elasticjob.spring.boot.annotation.ElasticJobConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author songfayuan
 * @date 2018/2/7
 * 测试Job
 */
@Slf4j
@Component
@ElasticJobConfig(cron = "0 0/5 * * * ? *", description = "测试案例")
public class SimpleJobTest implements SimpleJob {
    /**
     * 业务执行逻辑
     *
     * @param shardingContext 分片信息
     */
    @Override
    public void execute(ShardingContext shardingContext) {
        log.warn("shardingContext:{}", shardingContext);
    }
}
