package com.zzz.consumer.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {


    @Autowired
    private RedisTemplate redisTemplate;

    public Long getUniqekey(String tableName) {
        Long uId = redisTemplate.opsForValue().increment(tableName);
        return uId;
    }
}
