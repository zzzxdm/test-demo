package com.zzz.demo.utils;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RedisUtil {

    private static RedisTemplate redisTemplate;
    private static DefaultRedisScript<List> getRedisScript;

    private static final Long RELEASE_SUCCESS = 1L;


    @PostConstruct
    public void init() {
        getRedisScript = new DefaultRedisScript<>();
        getRedisScript.setResultType(List.class);
        getRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("luascript/releaseLock.lua")));
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisUtil.redisTemplate = redisTemplate;
    }


    /**
     * 获取锁
     *
     * @param lockKey
     * @param requestId
     * @return
     */
    public static boolean getLock(String lockKey, String requestId) {
        Boolean result = (Boolean) redisTemplate.execute((RedisCallback<Boolean>) redisConnection ->
                redisConnection.set(lockKey.getBytes(), requestId.getBytes(), Expiration.seconds(5), RedisStringCommands.SetOption.SET_IF_ABSENT));
        return result;
    }

    /**
     * 释放锁
     *
     * @param lockKey
     * @param requestId
     */
    public static boolean releaseLock(String lockKey, String requestId) {
        Map<String, Object> argvMap = new HashMap<>();
        argvMap.put("requestId", requestId);
        List<Long> execute = (List<Long>) redisTemplate.execute(getRedisScript, Lists.newArrayList(lockKey), argvMap);
        return execute.get(0).equals(RELEASE_SUCCESS);
    }
}
