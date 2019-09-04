package com.zzz.test.courrent_test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RRWLTest {

    public static void main(String[] args) {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(new Test(readLock, null, 0, "A", latch)).start();
        new Thread(new Test(null, writeLock, 2000, "B", latch)).start();
        new Thread(new Test(readLock, null, 0, "C", latch)).start();
        new Thread(new Test(null, writeLock, -500, "D", latch)).start();
        new Thread(new Test(readLock, null, 0, "E", latch)).start();
        new Thread(new Test(readLock, null, 0, "F", latch)).start();
        new Thread(new Test(null, writeLock, 600, "G", latch)).start();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        latch.countDown();


    }

    static class Test implements Runnable {
        public static volatile int account = 0;
        private ReentrantReadWriteLock.ReadLock readLock;
        private ReentrantReadWriteLock.WriteLock writeLock;
        private int money;
        private String name;
        private CountDownLatch latch;


        @Override
        public void run() {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (null != readLock) {
                readLock.lock();
                System.out.println(String.format("name:%s,查询余额：%s", name, account));
                readLock.unlock();
            } else {
                writeLock.lock();
                account += money;
                if (money > 0) {
                    System.out.println(String.format("name:%s,存款：%s,余额：%s", name, money, account));
                } else {
                    if (account > 0) {
                        System.out.println(String.format("name:%s,取款：%s,余额：%s", name, -money, account));
                    } else {
                        System.out.println(String.format("name:%s,取款：%s,余额：%s,取款失败", name, -money, account));
                        account -= money;
                    }
                }
                writeLock.unlock();
            }
        }


        public Test(ReentrantReadWriteLock.ReadLock readLock, ReentrantReadWriteLock.WriteLock writeLock, int money, String name, CountDownLatch latch) {
            super();
            this.readLock = readLock;
            this.writeLock = writeLock;
            this.money = money;
            this.name = name;
            this.latch = latch;
        }
    }

}
