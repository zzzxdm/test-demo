package com.zzz.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;

/**
 * 动态定时任务执行
 * @author songfayuan
 * @date 2021/4/26 10:56 上午
 */
@Slf4j
public class DynamicJob implements SimpleJob {

    /**
     * 业务执行逻辑
     *
     * @param shardingContext
     */
    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("动态定时任务执行逻辑start...");
        String jobName = shardingContext.getJobName();
        String jobParameter = shardingContext.getJobParameter();
        log.info("---------DynamicJob---------动态定时任务正在执行:jobName = {}, jobParameter = {}", jobName, jobParameter);
        //根据参数调用不同的业务接口处理，请远程调用业务模块处理，避免本服务与业务依赖过重...
        log.info("{}动态定时任务执行逻辑end...");
    }

}


