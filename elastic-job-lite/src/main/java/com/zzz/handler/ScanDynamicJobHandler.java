package com.zzz.handler;

import com.zzz.entity.JobDynamicTask;
import com.zzz.job.DynamicJob;
import com.zzz.service.ElasticJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class ScanDynamicJobHandler {
    @Resource
    private ElasticJobHandler elasticJobHandler;
    @Resource
    private ElasticJobService elasticJobService;

    /**
     * 扫描动态任务列表，并添加任务
     * <p>
     * 循环执行的动态任务，本服务重启的时候，需要重新加载任务
     *
     * @author songfayuan
     * @date 2021/4/26 9:15 下午
     */
    public void scanAddJob() {
        // 这里为从MySQL数据库读取job_dynamic_task表的数据，微服务项目中建议使用feign从业务服务获取
        // 避免本服务过度依赖业务的问题，然后业务服务新增动态任务也通过feign调取本服务JobOperateController实现
        // 从而相对独立本服务模块
        List<JobDynamicTask> jobDynamicTaskList = this.elasticJobService.getAllList();
        log.info("扫描动态任务列表，并添加任务：本次共扫描到{}条任务。", jobDynamicTaskList.size());
        for (JobDynamicTask jobDynamicTask : jobDynamicTaskList) {
            /******创建任务******/
            try {
                elasticJobHandler.addJob(jobDynamicTask.getJobName(), jobDynamicTask.getCron(),
                        1, new DynamicJob(), jobDynamicTask.getParameters(),
                        jobDynamicTask.getDescription());
            } catch (Exception e) {
                log.error("恢复Job错误:{}", e);
            }
        }
    }
}
