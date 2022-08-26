package com.zzz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Desc:
 * @Author zzz
 * @Date 2022/8/26
 * @Version V1.0
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.zzz.mapper"})
public class JobApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class, args);
    }
}
