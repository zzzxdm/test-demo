package com.cjs.example.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZZZ
 * @version 1.0
 * @date 2019/5/7 0007
 */
@Data
@NoArgsConstructor
public class Member {

    private String name;
    private int age;
    private String email;
}
