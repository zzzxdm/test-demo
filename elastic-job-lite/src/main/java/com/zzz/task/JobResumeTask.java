package com.zzz.task;

import com.zzz.handler.ScanDynamicJobHandler;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Desc:
 * @Author zzz
 * @Date 2022/9/22
 * @Version V1.0
 */
@Component
public class JobResumeTask implements ApplicationRunner {

    @Resource
    ScanDynamicJobHandler scanDynamicJobHandler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        scanDynamicJobHandler.scanAddJob();
    }

}
