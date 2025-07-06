package com.zzz.springdemo.controller;


import com.zzz.springdemo.service.TestService;
import com.zzz.springdemo.task.ScheduleTask;
import com.zzz.springdemo.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {


    @Resource
    TestService testService;

    private final ScheduleTask scheduleTask;

    @Autowired
    public TestController(ScheduleTask scheduleTask) {
        this.scheduleTask = scheduleTask;
    }

    @GetMapping("/hello")
    public Result hello() {
        testService.testHello();
        return Result.success("Hello World");
    }


    @RequestMapping("/test")
    public Result test() {
        testService.testMultiThread();
        return Result.success("Just For Test");
    }


    @GetMapping("/updateCron")
    public String updateCron(String cron) {
        log.info("new cron :{}", cron);
        scheduleTask.setCron(cron);
        return "ok";
    }


}
