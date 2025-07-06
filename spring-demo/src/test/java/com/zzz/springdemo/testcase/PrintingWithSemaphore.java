package com.zzz.springdemo.testcase;

import java.util.concurrent.Semaphore;

public class PrintingWithSemaphore {
    private static final Semaphore semaphoreNumber = new Semaphore(1);
    private static final Semaphore semaphoreLetter = new Semaphore(0);

    public static void main(String[] args) {
        Thread numberThread = new Thread(new NumberPrinter());
        Thread letterThread = new Thread(new LetterPrinter());

        numberThread.start();
        letterThread.start();
    }

    static class NumberPrinter implements Runnable {
        @Override
        public void run() {
            try {
                for (int i = 1; i <= 3; i++) {
                    semaphoreNumber.acquire();
                    System.out.print(i);
                    semaphoreLetter.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class LetterPrinter implements Runnable {
        @Override
        public void run() {
            try {
                for (char c = 'A'; c <= 'C'; c++) {
                    semaphoreLetter.acquire();
                    System.out.print(c);
                    semaphoreNumber.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
