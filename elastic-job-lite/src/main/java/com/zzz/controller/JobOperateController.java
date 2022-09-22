package com.zzz.controller;

import com.zzz.dto.JobAddDTO;
import com.zzz.handler.ElasticJobHandler;
import com.zzz.job.DynamicJob;
import com.zzz.service.ElasticJobService;
import com.zzz.utils.CronUtil;
import org.apache.commons.lang3.StringUtils;
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
    @Resource
    ElasticJobService elasticJobService;

    /**
     * 创建动态定时任务
     * jobName 任务名称
     * cron cron表达式 0 * * * * ? *
     *
     * @return
     */
    @GetMapping("/createJob")
    public String createJob(JobAddDTO jobAddDTO) {
        String jobName = jobAddDTO.getJobName();
        if (Objects.isNull(jobName)) {
            return "jobName不能为空";
        }
        boolean exist = elasticJobService.checkJob(jobName);
        if (exist) {
            return "任务已存在!";
        }
        String cron = jobAddDTO.getCron();
        if (StringUtils.isEmpty(cron)) {
            cron = CronUtil.time2Cron(jobAddDTO.getExecTime());
        }
        elasticJobHandler.addJob(jobName,
                cron,
                1,
                new DynamicJob(),
                jobAddDTO.getJobParams(),
                jobAddDTO.getDesc()
        );
        // 保留任务记录
        elasticJobService.saveJobTask(jobAddDTO);
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

    /**
     * 暂停定时任务
     * jobName 任务名称
     *
     * @return
     */
    @GetMapping("/pauseJob")
    public String pauseJob(@RequestBody Map<String, Object> params) {
        if (Objects.isNull(params.get("jobName"))) {
            return "jobName不能为空";
        }
        elasticJobHandler.pauseJob(params.get("jobName").toString());
        return "请求成功";
    }

}


