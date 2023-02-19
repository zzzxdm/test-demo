package com.zzz;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TestCase {

    @Test
    public void sleepSort() throws InterruptedException {
        int[] data = new int[]{5, 2, 3, 7, 9, 4};

        CountDownLatch downLatch = new CountDownLatch(data.length);
        for (int i = 0; i < data.length; i++) {
            int idx = i;
            new Thread(() -> {
                try {
                    TimeUnit.MICROSECONDS.sleep(data[idx] * 1000);
                    downLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print(data[idx] + "\t");
            }).start();
        }
        downLatch.await();
    }

    private void test(int value) {
        System.out.println("int");
    }

    private void test(String[] value) {
        System.out.println("array");
    }

    private void test(double value) {
        System.out.println("double");
    }

    @Test
    public void testType() {
        test(null);
    }
}
