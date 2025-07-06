package com.zzz.springdemo.testcase;

public class PrintingWithWaitNotify {
    private static final Object lock = new Object();
    private static boolean printNumber = true;

    public static void main(String[] args) {
        Thread numberThread = new Thread(new NumberPrinter());
        Thread letterThread = new Thread(new LetterPrinter());

        numberThread.start();
        letterThread.start();
    }

    static class NumberPrinter implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                try {
                    for (int i = 1; i <= 3; i++) {
                        while (!printNumber) {
                            lock.wait();
                        }
                        System.out.print(i);
                        printNumber = false;
                        lock.notify();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class LetterPrinter implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                try {
                    for (char c = 'A'; c <= 'C'; c++) {
                        while (printNumber) {
                            lock.wait();
                        }
                        System.out.print(c);
                        printNumber = true;
                        lock.notify();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}