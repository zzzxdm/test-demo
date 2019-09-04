package com.zzz.test.courrent_test;

import jodd.util.MathUtil;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestLock {
    
    public static void main(String[] args) throws InterruptedException {
        Task task = new Task();
        int count = 100;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        for (int i = 0; i < count; i++) {
            Random random = new Random();
            executorService.execute(() -> {
                int randomInt = random.nextInt(100);
                if (MathUtil.isOdd(randomInt)) {
                    new AddThread(task).run();
                } else {
                    new SubThread(task).run();
                }
            });
            TimeUnit.MILLISECONDS.sleep(random.nextInt(2000));
            countDownLatch.countDown();
        }
        executorService.shutdown();
        countDownLatch.await();
    }
}