package com.zzz.demo.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@PropertySource("classpath:swagger.properties")
@ConfigurationProperties(prefix = "user")
@Component
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}