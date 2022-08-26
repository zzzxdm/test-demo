package com.zzz.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzz.entity.JobDynamicTask;
import com.zzz.mapper.JobDynamicTaskMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
        // FIXME
        return Collections.emptyList();
    }
}
