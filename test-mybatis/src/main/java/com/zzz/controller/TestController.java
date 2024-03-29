package com.zzz.controller;

import com.zzz.service.CompanyServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class TestController {


    @Resource
    CompanyServiceImpl companyService;

    @GetMapping("/testBatchInsert")
    public String testBatchInsert(@RequestParam(defaultValue = "1000") Integer times) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        companyService.testBatchInsert(times);
        stopWatch.stop();
        long totalTimeMillis = stopWatch.getTotalTimeMillis();
        return "OK " + totalTimeMillis;
    }


    @GetMapping("/testHello")
    public String testHello() {
        log.info("Hello Word " + System.currentTimeMillis());
        return "Hello World";
    }


}
