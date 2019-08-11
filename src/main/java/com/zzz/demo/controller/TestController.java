package com.zzz.demo.controller;

import com.alibaba.fastjson.JSON;
import com.zzz.demo.entity.User;
import com.zzz.demo.intf.Compute;
import com.zzz.demo.intf.impl.ForkJoin;
import com.zzz.demo.intf.impl.ParllelStream;
import com.zzz.demo.intf.impl.Stream;
import com.zzz.demo.mapper.UserMapper;
import com.zzz.demo.service.RedissonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

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
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

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

    @ApiOperation("")
    @RequestMapping("/testSelect")
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
            redisTemplate.opsForHash().put("test", "name" + i, "test" + i);
        }
        Set<Object> test = redisTemplate.opsForHash().keys("test");
        for (Object key : test) {
            Object test1 = redisTemplate.opsForHash().get("test", key);
//            System.out.println(test1);
        }
        stopWatch.stop();
        System.out.printf("执行耗时:%fS", stopWatch.getTotalTimeSeconds());
    }
}
