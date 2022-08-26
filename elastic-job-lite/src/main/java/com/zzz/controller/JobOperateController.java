package com.zzz.controller;

import com.zzz.handler.ElasticJobHandler;
import com.zzz.job.DynamicJob;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 动态定时任务 前端控制器
 * </p>
 *
 * @author songfayuan
 * @date 2021/4/26 11:05 上午
 */
@RestController
@RequestMapping("/")
public class JobOperateController {

    @Resource
    private ElasticJobHandler elasticJobHandler;

    /**
     * 创建动态定时任务
     * jobName 任务名称
     * cron cron表达式 0 * * * * ? *
     *
     * @param params
     * @return
     */
    @GetMapping("/createJob")
    public String createJob(@RequestBody Map<String, Object> params) {
        if (Objects.isNull(params.get("jobName"))) {
            return "jobName不能为空";
        }
        if (Objects.isNull(params.get("cron"))) {
            return "cron不能为空";
        }
        elasticJobHandler.addJob(params.get("jobName").toString(),
                params.get("cron").toString(),
                1,
                new DynamicJob(),
                Objects.isNull(params.get("params")) ? "" : params.get("params").toString(),
                Objects.isNull(params.get("description")) ? "" : params.get("description").toString()
        );
        return "请求成功";
    }


    /**
     * 更新定时任务（似乎，好像，他内内的这个方法没用！！！）
     * jobName
     * cron cron表达式 0 0/5 * * * ?
     *
     * @return
     */
    @GetMapping("/updateJob")
    public String updateJob(@RequestBody Map<String, Object> params) {
        if (Objects.isNull(params.get("jobName"))) {
            return "jobName不能为空";
        }
        if (Objects.isNull(params.get("cron"))) {
            return "cron不能为空";
        }
        elasticJobHandler.updateJob(params.get("jobName").toString(), params.get("cron").toString());
        return "请求成功";
    }

    /**
     * 删除定时任务
     * jobName 任务名称
     *
     * @return
     */
    @GetMapping("/removeJob")
    public String removeJob(@RequestBody Map<String, Object> params) {
        if (Objects.isNull(params.get("jobName"))) {
            return "jobName不能为空";
        }
        elasticJobHandler.removeJob(params.get("jobName").toString());
        return "请求成功";
    }

}


