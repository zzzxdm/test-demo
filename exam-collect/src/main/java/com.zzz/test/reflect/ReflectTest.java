package com.zzz.test.reflect;

import java.lang.reflect.Field;

public class ReflectTest {

    final String str = "java";

    public String getStr() {
        return str;
    }

    public ReflectTest() {
    }


    public static void main(String[] args) throws Exception {
        ReflectTest reflectTest = new ReflectTest();
        Class<? extends ReflectTest> clazz = reflectTest.getClass();
        Field strField = clazz.getDeclaredField("str");
        strField.setAccessible(true);
        strField.set(clazz, "test");
        System.out.println(reflectTest.getStr());
    }
}
