package com.zzz.test.courrent_test;

public class SubThread implements Runnable {
    
    private Task task;
    
    public SubThread(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        task.consumer();
    }

}