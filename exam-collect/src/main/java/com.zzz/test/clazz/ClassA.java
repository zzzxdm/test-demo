package com.zzz.test.clazz;

public class ClassA {

    static void print() {
        System.out.println("a");
    }

    public ClassA() {
        System.out.println("Instance A");
    }

    static {
        System.out.println("静态块A");
    }
}