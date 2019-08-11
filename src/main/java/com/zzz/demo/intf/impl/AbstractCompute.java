package com.zzz.demo.intf.impl;

import com.zzz.demo.entity.Article;
import com.zzz.demo.intf.Compute;
import com.zzz.demo.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class AbstractCompute implements Compute {

    public List<Article> articles = new ArrayList<>();

    private RedisTemplate<String, Object> redisTemplate = (RedisTemplate<String, Object>) SpringUtils.getBean("redisTemplate");

    @Override
    public List<Article> initData() {
        String key = "test-data";
        redisTemplate.setKeySerializer(RedisSerializer.string());
        ValueOperations operations = redisTemplate.opsForValue();
        if (operations.get(key) != null) {
            articles = (List<Article>) operations.get(key);
        }
        log.info("测试数据条数:{}", articles.size());
        return articles;
    }
}
