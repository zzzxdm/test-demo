package com.zzz.test.clazz;

import lombok.Data;

@Data
public class User extends Person {
    private String userNo;

    public User() {
    }

    public String toString() {
        return "User{" +
                "userNo='" + userNo + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
