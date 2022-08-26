package com.zzz.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.zen.elasticjob.spring.boot.annotation.ElasticJobConfig;

import java.util.List;

/**
 * @author songfayuan
 * @date 2018/2/8
 */
@ElasticJobConfig(cron = "0 45 0 * * ? *", shardingTotalCount = 3,
        shardingItemParameters = "0=Beijing,1=Shanghai,2=Guangzhou",
        description = "测试案例")
public class AmazonDataflowJob implements DataflowJob<Integer> {


    @Override
    public List<Integer> fetchData(ShardingContext shardingContext) {
        return null;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<Integer> list) {

    }
}

