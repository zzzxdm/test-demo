package com.zzz.springdemo.testcase;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintingWithReentrant {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        Thread thread1 = new Thread(new PrintNumbers(lock, condition));
        Thread thread2 = new Thread(new PrintLetters(lock, condition));

        thread1.start();
        thread2.start();
    }
}

class PrintNumbers implements Runnable {
    private Lock lock;
    private Condition condition;

    public PrintNumbers(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 3; i++) {
            lock.lock();
            try {
                System.out.print(i);
                condition.signal(); // 唤醒打印字母的线程
                if (i < 3) {
                    condition.await(); // 等待打印字母的线程执行
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
    }
}

class PrintLetters implements Runnable {
    private Lock lock;
    private Condition condition;

    public PrintLetters(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        for (char letter = 'A'; letter <= 'C'; letter++) {
            lock.lock();
            try {
                System.out.print(letter);
                condition.signal(); // 唤醒打印数字的线程
                if (letter < 'C') {
                    condition.await(); // 等待打印数字的线程执行
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
    }
}
