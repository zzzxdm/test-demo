package com.zzz.test.clazz;

public class ClassB extends ClassA {

    static void print() {
        System.out.println("b");
    }
    static {
        System.out.println("静态块B");
    }

    public ClassB() {
        System.out.println("Instance B");
    }
}
