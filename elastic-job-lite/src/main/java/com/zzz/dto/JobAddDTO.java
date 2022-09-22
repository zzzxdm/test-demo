package com.zzz.dto;

import lombok.Data;

/**
 * @Desc:
 * @Author zzz
 * @Date 2022/9/22
 * @Version V1.0
 */
@Data
public class JobAddDTO {
    String jobName;
    String jobParams;
    String execTime;
    String cron;
    String desc;
}
