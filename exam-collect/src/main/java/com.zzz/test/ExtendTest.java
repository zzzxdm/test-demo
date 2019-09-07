package com.zzz.test;

import com.zzz.test.clazz.ClassA;
import com.zzz.test.clazz.ClassB;
import com.zzz.test.clazz.Person;
import com.zzz.test.clazz.User;

public class ExtendTest {


    public static void main(String[] args) {
        ClassA classA = new ClassB();
        classA = new ClassB();

        Person person = new Person();
        Person person1 = new User();

        Integer integer = new Integer(4);
        Long long1 = new Long(100L);

        // 不同类型相加往大的转
        long1 = integer + long1;

    }

}
