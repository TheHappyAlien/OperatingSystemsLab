package com.company;

public class Process {
    private final int requiredPower;
    private final int arrivalTime;
    private int length;

    public Process(int requiredPower, int arrivalTime, int length) {
        this.requiredPower = requiredPower;
        this.arrivalTime = arrivalTime;
        this.length = length;
    }

    public Process(Process process){
        this.requiredPower = process.getRequiredPower();
        this.arrivalTime = process.getArrivalTime();
        this.length = process.getLength();
    }

    public int getRequiredPower() {
        return requiredPower;
    }

    public int getLength() {
        return length;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
