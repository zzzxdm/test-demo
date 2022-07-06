package com.cjs.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ZZZ
 * @version 1.0
 * @date 2019/5/7 0007
 */
@SpringBootApplication
@MapperScan("com.cjs.example.mapper")
public class DynamicDatasourceDemo {
    public static void main(String[] args) {
        SpringApplication.run(DynamicDatasourceDemo.class, args);
    }
}
