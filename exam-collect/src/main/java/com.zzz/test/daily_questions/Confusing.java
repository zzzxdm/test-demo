package com.zzz.test.daily_questions;

public class Confusing {
    public Confusing(Object o) {
        System.out.println("Object");
    }

    public Confusing(double[] dArray) {
        System.out.println("double array");
    }

    public static void main(String args[]) {
        // 默认参数是数组
        new Confusing(null);
    }
}