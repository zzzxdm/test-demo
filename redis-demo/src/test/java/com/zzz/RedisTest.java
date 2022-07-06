package com.zzz;

import com.zzz.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Desc:
 * @Author zzz
 * @Date 2022/7/6
 * @Version V1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisTest.class)
@ComponentScan("com.zzz")
public class RedisTest {

    @Resource
    RedisTemplate redisTemplate;

    Logger logger = LoggerFactory.getLogger(RedisTest.class);

    @Test
    public void testForLoopAddSingleString() throws Exception {
        long startTime = System.currentTimeMillis();
        String redisValue = "";
        for (int i = 0; i < 10000; i++) {
            StringBuilder redisKey = new StringBuilder();
            redisKey.append("key_").append(i);
            redisValue = String.valueOf(i);
            redisTemplate.opsForValue().set(redisKey.toString(), redisValue);
        }
        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;
        logger.info(">>>>>>test batch add into redis by using normal for loop spent->" + costTime);

    }


    @Test
    public void testPipelineAddSingleString() throws Exception {
        RedisSerializer<String> keySerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
        RedisSerializer<Object> valueSerializer = (RedisSerializer<Object>) redisTemplate.getValueSerializer();
        long startTime = System.currentTimeMillis();
        redisTemplate.executePipelined((RedisCallback<Object>) pipeLine -> {
            try {
                String redisValue = "";
                for (int i = 0; i < 10000; i++) {
                    StringBuilder redisKey = new StringBuilder();
                    redisKey.append("key_").append(i);
                    redisValue = String.valueOf(i);
                    pipeLine.setEx(keySerializer.serialize(redisKey.toString()), 10,
                            valueSerializer.serialize(redisValue));
                }
            } catch (Exception e) {
                logger.error(">>>>>>test batch add into redis by using pipeline for loop error: " + e.getMessage(), e);
            }
            return null;
        });
        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;
        logger.info(">>>>>>test batch add into redis by using pipeline for loop spent->" + costTime);
    }

    @Test
    public void testPipelineAddHash() throws Exception {
        RedisSerializer<String> keySerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
        RedisSerializer<Object> valueSerializer = (RedisSerializer<Object>) redisTemplate.getValueSerializer();
        long startTime = System.currentTimeMillis();
        String hashKey = "pipeline_hash_key";
        redisTemplate.executePipelined((RedisCallback<Object>) pipeLine -> {
            try {
                String redisValue = "";
                for (int i = 0; i < 10000; i++) {
                    StringBuilder redisKey = new StringBuilder();
                    redisKey.append("key_").append(i);
                    redisValue = String.valueOf(i);
                    pipeLine.hSet(keySerializer.serialize(hashKey), keySerializer.serialize(redisKey.toString()),
                            valueSerializer.serialize(redisValue));
                }
            } catch (Exception e) {
                logger.error(">>>>>>testPipelineAddHash by using pipeline for loop error: " + e.getMessage(), e);
            }
            return null;
        });
        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;
        logger.info(">>>>>>testPipelineAddHash by using pipeline for loop spent->" + costTime);
    }


    @Test
    public void testPipelineAddHashBean() throws Exception {
        RedisSerializer<String> keySerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
        Jackson2JsonRedisSerializer jacksonSerial = new Jackson2JsonRedisSerializer<>(Object.class);
        long startTime = System.currentTimeMillis();
        String hashKey = "pipeline_hash_key";
        redisTemplate.executePipelined((RedisCallback<Object>) pipeLine -> {
            try {
                String redisValue = "";
                for (int i = 0; i < 10000; i++) {
                    StringBuilder redisKey = new StringBuilder();
                    redisKey.append("key_").append(i);
                    Student student = new Student();
                    student.setId("001");
                    student.setName("chhliu");
                    student.setGrade("一年级");
                    student.setAge("28");
                    pipeLine.hSet(keySerializer.serialize(hashKey), keySerializer.serialize(redisKey.toString()),
                            jacksonSerial.serialize(student));
                }
            } catch (Exception e) {
                logger.error(">>>>>>testPipelineAddHash by using pipeline for loop error: " + e.getMessage(), e);
            }
            return null;
        });
        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;
        logger.info(">>>>>>testPipelineAddHash by using pipeline for loop spent->" + costTime);
    }

}
