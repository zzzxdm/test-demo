package com.zzz.demo.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson 配置类
 * Created on 2018/6/19
 */
@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient getRedisson() {
        Config config = new Config();
        config.useClusterServers().addNodeAddress("redis://106.12.35.24:7000").setPassword("admin");
//        config.useSingleServer().setAddress("redis://106.12.35.24:6379").setPassword("admin");
        return Redisson.create(config);
    }

}
