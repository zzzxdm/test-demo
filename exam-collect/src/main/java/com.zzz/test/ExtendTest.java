package com.zzz.test;

import com.zzz.test.clazz.ClassA;
import com.zzz.test.clazz.ClassB;
import com.zzz.test.clazz.Person;
import com.zzz.test.clazz.User;

public class ExtendTest {


    public static void main(String[] args) {
        // 父类静态块-->子类静态块-->父类构造器-->子类构造器
        ClassA classA = new ClassB();
        // 父类构造器-->子类构造器（静态块只初始化一次）
        classA = new ClassB();

        // 1.父类是子类构造出的实例，父类才能强转成子类
        Person person = new User();
        User user = new User();
        user.setUserNo("123");
        user.setName("zzz");
        System.out.println(person);
        System.out.println(user);
        // 必须满足条件@1
        user = (User) person;
        System.out.println(user);

        Integer integer = new Integer(4);
        Long long1 = new Long(100L);

        // 不同类型相加往大的转
        long1 = integer + long1;
        System.out.println(long1);

    }

}
