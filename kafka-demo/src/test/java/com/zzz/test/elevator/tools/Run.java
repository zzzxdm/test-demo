package com.zzz.test.elevator.tools;

import com.zzz.test.elevator.constant.ElevatorConstant;
import com.zzz.test.elevator.constant.Status;
import com.zzz.test.elevator.entity.Elevator;
import jodd.util.collection.SortedArrayList;
import org.apache.commons.collections.CollectionUtils;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class Run extends AbstractExecutor {

    private volatile int floor;

    public Run(Elevator elevator, int floor) {
        super(elevator);
        this.floor = floor;


    }

    @Override
    public void add(int floor) {
        this.floor = floor;
        Elevator elevator = super.getElevator();
        checkFloor(floor);
        // in current floor
        int currentFloor = elevator.getFloor();
        if (currentFloor == this.floor) return;
        // go
        if (currentFloor < this.floor) {
            SortedArrayList<Integer> upNode = elevator.getUpNode();
            synchronized (upNode) {
                if (!upNode.contains(floor)) {
                    upNode.add(floor);
                }
            }
        } else {
            SortedArrayList<Integer> downNode = elevator.getDownNode();
            synchronized (downNode) {
                if (!downNode.contains(floor)) {
                    downNode.add(floor);
                }
            }
        }
    }

    @Override
    public void execute() {
        Elevator elevator = super.getElevator();
        System.out.println("当前楼层:" + elevator.getFloor());
        add(this.floor);
        // in current floor
        int currentFloor = elevator.getFloor();
        while (CollectionUtils.isNotEmpty(elevator.getDownNode())
                || CollectionUtils.isNotEmpty(elevator.getUpNode())) {
            // go
            if (currentFloor < this.floor) {
                // up
                up(elevator);
                // down
                down(elevator);
            } else {
                // down
                down(elevator);
                // up
                up(elevator);
            }
            // reset
            reset(elevator);
        }
    }

    private void reset(Elevator elevator) {
        this.floor = 0;
        if (elevator.getFloor() != 0) {
            execute();
        }
    }

    private void down(Elevator elevator) {
        elevator.setStatus(Status.UP);
        SortedArrayList<Integer> floorInfo = elevator.getDownNode();
        synchronized (floorInfo) {
            Iterator<Integer> iterator = floorInfo.iterator();
            while (iterator.hasNext()) {
                Integer next = iterator.next();
                imitateDelay(Math.abs(next - elevator.getFloor()));
                System.out.printf("%d楼到了\n", next);
                elevator.setFloor(next);
                iterator.remove();
            }
        }
    }

    private void up(Elevator elevator) {
        elevator.setStatus(Status.DOWN);
        SortedArrayList<Integer> floorInfo = elevator.getUpNode();
        synchronized (floorInfo) {
            Iterator<Integer> iterator = floorInfo.iterator();
            while (iterator.hasNext()) {
                Integer next = iterator.next();
                imitateDelay(Math.abs(next - elevator.getFloor()));
                System.out.printf("%d楼到了\n", next);
                elevator.setFloor(next);
                iterator.remove();
            }
        }
    }

    private void checkFloor(int floor) {
        if (floor > ElevatorConstant.MAX_FLOOR
                || floor < ElevatorConstant.MIN_FLOOR) {
            System.err.println("非法楼层");
        }
    }

    private void imitateDelay(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
