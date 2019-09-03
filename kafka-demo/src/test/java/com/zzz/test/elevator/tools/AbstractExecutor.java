package com.zzz.test.elevator.tools;

import com.zzz.test.elevator.entity.Elevator;

public class AbstractExecutor implements Command {

    private Elevator elevator;

    public AbstractExecutor(Elevator elevator) {
        this.elevator = elevator;
    }

    public Elevator getElevator() {
        return elevator;
    }

    public void setElevator(Elevator elevator) {
        this.elevator = elevator;
    }

    @Override
    public void add(int floor) {

    }

    @Override
    public void execute() {

    }
}
