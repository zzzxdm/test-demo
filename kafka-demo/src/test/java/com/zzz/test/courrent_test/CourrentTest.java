package com.zzz.test.courrent_test;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CourrentTest {

    @Test
    public void countDownLatchTest() throws InterruptedException {
        int count = 5;
        CountDownLatch begin = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(count);
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        for (int i = 0; i < count; i++) {
            executorService.submit(() -> {
                try {
                    Random random = new Random();
                    int timeout = random.nextInt(5);
                    String threadName = getThreadName();
                    System.out.println(threadName + "准备跑了，先休息" + timeout + "秒");
                    begin.await();
                    TimeUnit.SECONDS.sleep(timeout);
                    System.out.println(threadName + "跑完了");
                    end.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
        begin.countDown();
        System.out.println("所有线程开始执行");
        end.await();
        System.out.println("所有线程执行完毕");
    }

    private String getThreadName() {
        return Thread.currentThread().getName();
    }

}
