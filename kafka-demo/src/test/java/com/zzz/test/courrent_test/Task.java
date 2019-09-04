package com.zzz.test.courrent_test;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Task {

    private final Lock lock = new ReentrantLock();

    private final Condition addCondition = lock.newCondition();

    private final Condition subCondition = lock.newCondition();


    private static int num = 0;
    private Queue<String> queue = new ArrayBlockingQueue(10);

    public void producer() {
        lock.lock();
        try {
            while (queue.size() == 10) {
                addCondition.await();
            }
            num++;
            queue.add("producer Banana" + num);
            System.out.println("The Queue Size is " + queue.size());
            System.out.println("The Current Thread is " + Thread.currentThread().getName());
            this.subCondition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {//释放锁
            lock.unlock();
        }
    }


    public void consumer() {
        lock.lock();
        try {
            while (queue.size() == 0) {
                subCondition.await();
            }
            String str = queue.poll();
            System.err.println("The Token Banana is [" + str + "]");
            System.err.println("The Current Thread is " + Thread.currentThread().getName());
            num--;
            addCondition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}