package com.zzz.demo.controller;

import com.alibaba.fastjson.JSON;
import com.zzz.demo.entity.User;
import com.zzz.demo.intf.Compute;
import com.zzz.demo.intf.impl.ForkJoin;
import com.zzz.demo.intf.impl.ParllelStream;
import com.zzz.demo.intf.impl.Stream;
import com.zzz.demo.mapper.UserMapper;
import com.zzz.demo.service.RedissonService;
import com.zzz.demo.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/test")
@Slf4j
@Api(tags = "TestController", description = "测试接口")
public class TestController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private RedissonService redissonService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedisUtil redisUtil;

    private static Environment environment;


    public TestController(Environment environment) {
        TestController.environment = environment;
        System.err.println(TestController.environment);
    }

    @ApiOperation("Test Hello")
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World";
    }

    @PostMapping("/testUser")
    public String testUser(User user) {
        String userInfo = JSON.toJSONString(user);
        System.out.println(userInfo);
        return userInfo;
    }

    @PostMapping("/testUser2")
    public String testUser2(@RequestBody User user) {
        String userInfo = JSON.toJSONString(user);
        System.out.println(userInfo);
        return userInfo;
    }

    @GetMapping("/testSelect")
    public List<User> testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
        return userList;
    }

    @GetMapping("testCompute")
    public void testCompute() {
        StopWatch stopWatch = new StopWatch();
        Compute stream = new Stream();
        Compute parllelStream = new ParllelStream();
        Compute forkJoin = new ForkJoin();
        stream.calculate();
        parllelStream.calculate();
        stopWatch.start();
        forkJoin.calculate();
        stopWatch.stop();
        log.info("ForkJoin并行流耗时:{}s", stopWatch.getTotalTimeSeconds());
    }

    @GetMapping("/testRedisson")
    public void testRedisson() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 1000; i++) {
            stringRedisTemplate.opsForHash().put("test", "name" + i, "test" + i);
        }
        Set<Object> test = stringRedisTemplate.opsForHash().keys("test");
        for (Object key : test) {
            Object test1 = stringRedisTemplate.opsForHash().get("test", key);
//            System.out.println(test1);
        }
        stopWatch.stop();
        System.out.printf("执行耗时:%fS", stopWatch.getTotalTimeSeconds());
    }


    @GetMapping("/lockTest")
    public void lockTest() {
        int threadCount = 20;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                String uuid = UUID.randomUUID().toString();
                String lockKey = "LockTest.testLock";
                try {
                    boolean lock = redisUtil.getLock(lockKey, uuid);
                    Object value = redisTemplate.opsForValue().get(lockKey);
                    System.out.println(value);
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
        }
        executorService.shutdown();
    }

}
