package com.zzz.test.clazz;

public class ClassC {

    private static ClassC instance = new ClassC();
    private static int count = 1;

    public static void getInstance() {
        System.out.println(count);
    }

    public ClassC() {
        instance.getInstance();
    }

    public static void main(String[] args) {
        new ClassC();
    }
}
