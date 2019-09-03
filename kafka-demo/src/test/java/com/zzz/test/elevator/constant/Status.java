package com.zzz.test.elevator.constant;

public enum Status {

    UP(1, "UP"), DOWN(2, "DOWN"), STOP(0, "STOP"), OPEN(3, "OPEN"), CLOSE(4, "CLOSE");

    private int status;
    private String value;

    Status(int status, String value) {
        this.status = status;
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static Status getStatus(String name) {
        for (Status status : values()) {
            if (status.value.equalsIgnoreCase(name)) {
                return status;
            }
        }
        return null;
    }
}
