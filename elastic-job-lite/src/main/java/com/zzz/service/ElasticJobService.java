package com.zzz.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzz.dto.JobAddDTO;
import com.zzz.entity.JobDynamicTask;
import com.zzz.mapper.JobDynamicTaskMapper;
import com.zzz.utils.CronUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Desc:
 * @Author zzz
 * @Date 2022/8/26
 * @Version V1.0
 */
@Service
public class ElasticJobService extends ServiceImpl<JobDynamicTaskMapper, JobDynamicTask> {

    public List<JobDynamicTask> getAllList() {
        LambdaQueryWrapper<JobDynamicTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JobDynamicTask::getIsDelete, 0);
        return this.list(queryWrapper);
    }

    public void saveJobTask(JobAddDTO jobAddDTO) {
        JobDynamicTask jobDynamicTask = new JobDynamicTask();
        jobDynamicTask.setJobName(jobAddDTO.getJobName());
        String cron = jobAddDTO.getCron();
        if (StringUtils.isEmpty(cron)) {
            cron = CronUtil.time2Cron(jobAddDTO.getExecTime());
        }
        jobDynamicTask.setCron(cron);
        jobDynamicTask.setDescription(jobAddDTO.getDesc());
        jobDynamicTask.setParameters(jobAddDTO.getJobParams());
        jobDynamicTask.setCreateTime(new Date());
        this.save(jobDynamicTask);
    }

    public boolean checkJob(String jobName) {
        LambdaQueryWrapper<JobDynamicTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JobDynamicTask::getJobName, jobName)
                .eq(JobDynamicTask::getIsDelete, 0);
        return this.count(queryWrapper) > 0;
    }

    public JobDynamicTask getByJobName(String jobName) {
        LambdaQueryWrapper<JobDynamicTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JobDynamicTask::getJobName, jobName)
                .eq(JobDynamicTask::getIsDelete, 0)
                .last("limit 1");
        return this.getOne(queryWrapper);
    }

    public void removeByJobName(String jobName) {
        LambdaQueryWrapper<JobDynamicTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JobDynamicTask::getJobName, jobName)
                .eq(JobDynamicTask::getIsDelete, 0);
        this.remove(queryWrapper);
    }
}
