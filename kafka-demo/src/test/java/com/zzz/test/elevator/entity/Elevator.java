package com.zzz.test.elevator.entity;

import com.zzz.test.elevator.constant.ElevatorConstant;
import com.zzz.test.elevator.constant.Status;
import jodd.util.collection.SortedArrayList;
import lombok.Data;

import java.util.Comparator;

@Data
public class Elevator {

    private volatile Enum status;
    private volatile int floor;
    private int maxFloor;
    private int minFloor;
    private SortedArrayList<Integer> upNode;
    private SortedArrayList<Integer> downNode;

    public Elevator() {
        this.status = Status.STOP;
        this.floor = 1;
        this.maxFloor = ElevatorConstant.MAX_FLOOR;
        this.minFloor = ElevatorConstant.MIN_FLOOR;
        upNode = new SortedArrayList();
        downNode = new SortedArrayList();
    }

    public Elevator(int floor) {
        this.floor = floor;
        this.status = Status.STOP;
        this.maxFloor = ElevatorConstant.MAX_FLOOR;
        this.minFloor = ElevatorConstant.MIN_FLOOR;
        upNode = new SortedArrayList();
        downNode = new SortedArrayList((Comparator<Integer>) (o1, o2) -> o2 - o1);
    }
}
