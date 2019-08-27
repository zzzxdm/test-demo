package com.zzz.demo;

import com.zzz.demo.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableDiscoveryClient
@MapperScan("com.zzz.demo.mapper")
public class TestDemoApplicationTests {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testLock() {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        executorService.execute(() -> {
            String uuid = UUID.randomUUID().toString();
            String lockKey = "LockTest.testLock";
            try {
                boolean lock = redisUtil.getLock(lockKey, uuid);
                String name = Thread.currentThread().getName();
                if (lock) {
                    System.out.println("线程: " + name + " 获取锁成功");
                } else {
                    System.err.println("线程: " + name + " 获取锁失败");
                }
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                redisUtil.releaseLock(lockKey, uuid);
            }
        });
        executorService.shutdown();
    }

}
