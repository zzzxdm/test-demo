package com.zzz.springdemo.testcase;

public class Case1 {

    static boolean flag = false;
    static final Object monitor = new Object();
    static int number = 1;
    static final int maxNum = 100;

    public static void main(String[] args) {

        Thread thread1 = new Thread(() -> {
            while (number <= maxNum) {
                synchronized (monitor) {
                    if (!flag) {
                        try {
                            monitor.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println(Thread.currentThread().getName() + "-" +  number);
                        number++;
                        flag = true;
                        monitor.notify();
                    }
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            while (number <= maxNum) {
                synchronized (monitor) {
                    if (flag) {
                        try {
                            monitor.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println(Thread.currentThread().getName() + "-" + number);
                        number++;
                        flag = false;
                        monitor.notify();
                    }
                }
            }
        });
        thread1.start();
        thread2.start();

    }

}
