package com.zzz.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.AbstractDistributeOnceElasticJobListener;

/**
 * ElasticJobListener 监听器
 *
 * 实现分布式任务监听器
 * 如果任务有分片，分布式监听器会在总的任务开始前执行一次，结束时执行一次
 *
 * @author songfayuan
 * @date 2021/4/26 2:28 下午
 */
public class ElasticJobListener extends AbstractDistributeOnceElasticJobListener {

    public ElasticJobListener(long startedTimeoutMilliseconds, long completedTimeoutMilliseconds) {
        super(startedTimeoutMilliseconds,completedTimeoutMilliseconds);
    }

    @Override
    public void doBeforeJobExecutedAtLastStarted(ShardingContexts shardingContexts) {
    }

    @Override
    public void doAfterJobExecutedAtLastCompleted(ShardingContexts shardingContexts) {
        //任务执行完成后更新状态为已执行，当前未处理
    }
}


