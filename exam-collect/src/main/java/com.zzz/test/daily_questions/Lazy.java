package com.zzz.test.daily_questions;

public class Lazy {

    private static volatile boolean initialized = false;

    static {
        Thread thread = new Thread(() -> {
            initialized = true;
        });
        /*Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                initialized = true;
            }
        });*/
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(initialized);
    }
}
