package com.company;

import java.util.Random;

public class ProcessSim{

    private final int assignedNumber;
    private final int timeOfArrival;
    private final int processingTime;
    private int remainingTime;
    private static int processNumber = 0;


    public ProcessSim(int timeOfArrival, int processingTime){
        assignedNumber = processNumber;
        this.timeOfArrival = timeOfArrival;
        this.processingTime = processingTime;
        remainingTime = processingTime;
        processNumber++;
    }

    public ProcessSim(ProcessSim processSim){
        this.assignedNumber = processSim.getAssignedNumber();
        this.timeOfArrival = processSim.getTimeOfArrival();
        this.processingTime = processSim.getProcessingTime();
        remainingTime = processingTime;
    }

    public static void resetProcessNumber(){
        processNumber = 0;
    }

    public int getAssignedNumber() {
        return assignedNumber;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public int getTimeOfArrival() {
        return timeOfArrival;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }
}
