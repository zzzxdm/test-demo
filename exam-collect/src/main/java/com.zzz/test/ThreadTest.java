package com.zzz.test;

import java.util.concurrent.TimeUnit;

/**
 * @author ZZZ
 * @version 1.0
 * @date 2019/10/16 0016
 */
public class ThreadTest {

    public void threadTest() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            sleep("t1", 10);
        });

        Thread t2 = new Thread(() ->{
            sleep("t2", 8);
        });

        Thread t3 = new Thread(() ->{
            sleep("t3", 5);
        });

        /*t1.start();
        t1.join();

        t2.start();
        t2.join();*/

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("all thread finished");
    }

    public void sleep(String name, int second) {
        try {
            System.out.println(name + " start");
            TimeUnit.SECONDS.sleep(second);
            System.out.println(name + " stop");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadTest threadTest = new ThreadTest();
        threadTest.threadTest();
    }
}
