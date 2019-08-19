package com.zzz.test.circular_references.bootstrap;

import com.zzz.test.circular_references.entity.BeanA;
import com.zzz.test.circular_references.entity.BeanB;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ZZZ
 * @version 1.0
 * @date 2019/8/19 0019
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("com.zz.test")
public class CircReferenceTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void contextLoads() {
        System.out.println("test...");
        BeanA beanA = applicationContext.getBean(BeanA.class);
        BeanB beanB = applicationContext.getBean(BeanB.class);
        System.out.println(beanA + "\n" + beanB);
    }
}
