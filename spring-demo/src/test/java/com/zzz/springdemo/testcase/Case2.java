package com.zzz.springdemo.testcase;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Case2 {
    static boolean flag = false;
    static final ReentrantLock lock = new ReentrantLock();
    static final Condition condition = lock.newCondition();
    static int number = 1;
    static final int maxNum = 100;

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            while (number <= maxNum) {
                try {
                    lock.lock();
                    if (!flag) {
                        condition.await();
                    } else {
                        System.out.println(Thread.currentThread().getName() + "-" + number);
                        flag = false;
                        number++;
                        condition.signal();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            while (number <= maxNum) {
                try {
                    lock.lock();
                    if (flag) {
                        condition.await();
                    } else {
                        System.out.println(Thread.currentThread().getName() + "-" + number);
                        flag = true;
                        number++;
                        condition.signal();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}