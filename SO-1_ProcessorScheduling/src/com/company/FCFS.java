package com.company;

import java.util.ArrayList;

public class FCFS implements Algorythm {

    private ArrayList<ProcessSim> processList;
    private Clock clock;

    private ProcessSim currentProcess;

    private float totalWaitTime = 0;
    private final float numberOfProcesses;


    public FCFS(ArrayList<ProcessSim> processList, Clock clock) {
        this.processList = processList;
        this.processList.sort(new ByTimeOfArrival());
        numberOfProcesses = processList.size();
        this.clock = clock;
    }

    public void update() {

        if (processList.size() > 0) {

            currentProcess = processList.get(0);

            if (currentProcess.getTimeOfArrival() < clock.getTime()) {

                currentProcess.setRemainingTime(currentProcess.getRemainingTime() - 1);

                if (currentProcess.getRemainingTime() == 0) {
                    totalWaitTime += (clock.getTime() - currentProcess.getProcessingTime() - currentProcess.getTimeOfArrival());
                    processList.remove(currentProcess);
                }

            }
        } else {
            System.out.println("FCFS: " + totalWaitTime / numberOfProcesses);
            clock.stopClock();
        }


    }

}
