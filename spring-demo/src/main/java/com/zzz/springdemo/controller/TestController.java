package com.zzz.springdemo.controller;


import com.zzz.springdemo.service.TestService;
import com.zzz.springdemo.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
public class TestController {


    @Resource
    TestService testService;

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


}
