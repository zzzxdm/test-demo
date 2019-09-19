package com.zzz.test;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AlternatePrint {

    static Object obj = new Object();
    volatile Integer num = 0;
    AtomicInteger aNum = new AtomicInteger(0);

    public static void main(String[] args) {

    }

    /**
     * lock + condition
     *
     * @throws InterruptedException
     */
    @Test
    public void Task1Test() throws InterruptedException {
        CountDownLatch downLatch = new CountDownLatch(1);
        Lock lock = new ReentrantLock(true);
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        int count = 100;

        new Thread(() -> {
            while (num < count) {
                lock.tryLock();
                String name = Thread.currentThread().getName();
                System.out.println(name + ": " + num++);
                try {
                    condition1.await();
                    condition2.signal();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                lock.unlock();
            }
            downLatch.countDown();
        }).start();

        new Thread(() -> {
            while (num < count) {
                lock.tryLock();
                String name = Thread.currentThread().getName();
                System.out.println(name + ": " + num++);
                try {
                    condition1.signal();
                    condition2.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                lock.unlock();
            }
            downLatch.countDown();
        }).start();
        downLatch.await();
    }


    /**
     * synchronized + notifyAll/wait
     *
     * @throws InterruptedException
     */
    @Test
    public void Task2Test() throws InterruptedException {
        CountDownLatch downLatch = new CountDownLatch(1);
        Task2 task2 = new Task2(downLatch);
        new Thread(task2).start();
        new Thread(task2).start();
        downLatch.await();
    }


    public class Task2 implements Runnable {
        CountDownLatch downLatch;

        public Task2(CountDownLatch downLatch) {
            this.downLatch = downLatch;
        }

        @Override
        public void run() {
            synchronized (obj) {
                while (aNum.getAndIncrement() < 100) {
                    String name = Thread.currentThread().getName();
                    System.out.println(name + ": " + aNum.get());
                    try {
                        obj.notifyAll();
                        obj.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                downLatch.countDown();
            }
        }
    }

}
