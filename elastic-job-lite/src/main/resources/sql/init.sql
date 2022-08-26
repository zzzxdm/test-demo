CREATE TABLE `job_dynamic_task`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT,
    `is_delete`   int(4)       NOT NULL DEFAULT '0' COMMENT '是否删除（0否 1是）',
    `create_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `job_name`    varchar(200) NOT NULL DEFAULT '' COMMENT '任务名称',
    `cron`        varchar(100) NOT NULL COMMENT 'cron表达式',
    `description` varchar(255) NOT NULL DEFAULT '' COMMENT '作业描述',
    `parameters`  text         NOT NULL COMMENT '参数',
    `status`      int(11)      NOT NULL DEFAULT '-1' COMMENT '状态：0未执行 1已执行',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4 COMMENT ='Elastic Job 自定义定时任务（动态任务）';
