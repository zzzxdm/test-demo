package com.zzz.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Desc:
 * @Author zzz
 * @Date 2022/6/7
 * @Version V1.0
 */
@Data
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String age;
    private String grade;
}
