package com.zzz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Desc:
 * @Author zzz
 * @Date 2022/8/26
 * @Version V1.0
 */
@Data
@TableName("job_dynamic_task")
public class JobDynamicTask {

    @TableId(type = IdType.AUTO)
    Long id;

    Integer isDelete;

    String jobName;

    String cron;

    String description;

    String parameters;

    int status;

    Date createTime;

    Date updateTime;

}
