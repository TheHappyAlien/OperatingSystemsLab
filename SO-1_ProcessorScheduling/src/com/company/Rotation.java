package com.company;

import java.util.ArrayList;

public class Rotation implements Algorythm{

    private ArrayList<ProcessSim> processList;
    private ArrayList<ProcessSim> arrivedProcesses = new ArrayList<>();
    private Clock clock;

    private ProcessSim currentProcess = null;

    private float totalWaitTime = 0;
    private final float numberOfProcesses;
    private int segmentTime;
    private int segmentTimer = 0;
    private int currentProcessNumber = 0;

    public Rotation(ArrayList<ProcessSim> processList, Clock clock, int segmentTime) {
        this.processList = processList;
        this.processList.sort(new ByTimeOfArrival());
        numberOfProcesses = processList.size();
        this.clock = clock;
        this.segmentTime = segmentTime;
    }

    @Override
    public void update() {

        if (processList.size() > 0 || arrivedProcesses.size() > 0) {

            if (processList.size() > 0) {
                while (processList.get(0).getTimeOfArrival() < clock.getTime()) {
                    arrivedProcesses.add(processList.get(0));
                    processList.remove(processList.get(0));
                    if (processList.size() == 0) {
                        break;
                    }
                }
            }

            if (currentProcess == null && arrivedProcesses.size() > 0){
                currentProcess = arrivedProcesses.get(currentProcessNumber);
            }

            if (currentProcess != null) {

                segmentTimer++;

                currentProcess.setRemainingTime(currentProcess.getRemainingTime() - 1);

                if (currentProcess.getRemainingTime() == 0) {
                    totalWaitTime += (clock.getTime() - currentProcess.getProcessingTime() - currentProcess.getTimeOfArrival());
                    arrivedProcesses.remove(currentProcess);
                    currentProcess = null;
                    if (currentProcessNumber >= arrivedProcesses.size() ){
                        currentProcessNumber = 0;
                    }
                    segmentTimer = 0;
                }else if (segmentTimer == segmentTime){
                    if (currentProcessNumber < arrivedProcesses.size() - 1){
                        currentProcessNumber++;
                    }else {
                        currentProcessNumber = 0;
                    }
                    segmentTimer = 0;
                }
            }
        } else {
            System.out.println("Rotation: " + totalWaitTime / numberOfProcesses);
            clock.stopClock();

        }
    }
}
