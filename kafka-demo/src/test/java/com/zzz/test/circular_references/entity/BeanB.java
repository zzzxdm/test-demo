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
public class BeanB {

    @Autowired
    private BeanA beanA;

    private String name = "BeanB";

    @Override
    public String toString() {
        return "BeanB{" +
                " name='" + name + '\'' +
                '}';
    }
}
