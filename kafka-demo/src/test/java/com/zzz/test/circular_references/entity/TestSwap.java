package com.zzz.test.circular_references.entity;

public class TestSwap {
    public static void main(String[] args) {
        int a = 1, b = 2;
        swap(a, b);
        System.out.println(a + "---" + b);
    }

    public static void swap(int a, int b) {
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.err.println(a + "---" + b);
    }

 }
