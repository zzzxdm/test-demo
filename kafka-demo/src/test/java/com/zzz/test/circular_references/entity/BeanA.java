package com.zzz.test.circular_references.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ZZZ
 * @version 1.0
 * @date 2019/8/19 0019
 */
@Component
@Data
public class BeanA {

    @Autowired
    private BeanB beanB;

    private String name = "BeanA";

    @Override
    public String toString() {
        return "BeanA{" +
                "beanB=" + beanB +
                ", name='" + name + '\'' +
                '}';
    }
}
