package com.zzz.test.elevator;

import com.zzz.test.elevator.entity.Elevator;
import com.zzz.test.elevator.tools.Command;
import com.zzz.test.elevator.tools.Run;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) {
        Elevator elevator1 = new Elevator(1);
        Command command = new Run(elevator1, 5);
        int count = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        new Thread(() -> {
            for (int i = 0; i < count; i++) {
                Random random = new Random();
                executorService.submit(() -> {
                    int floor = random.nextInt(19) + 1;
                    String name = Thread.currentThread().getName();
                    System.out.println(name + "要去" + floor + "楼");
                    command.add(floor);
                });
                try {
                    TimeUnit.SECONDS.sleep(random.nextInt(5));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            executorService.shutdown();
        }).start();
        command.execute();
    }
}
