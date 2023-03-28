package com.company;

import java.util.ArrayList;

public class SRJF implements Algorythm{

    private ArrayList<ProcessSim> processList;
    private ArrayList<ProcessSim> arrivedProcesses = new ArrayList<>();
    private Clock clock;

    private ProcessSim currentProcess = null;

    private float totalWaitTime = 0;
    private final float numberOfProcesses;

    public SRJF(ArrayList<ProcessSim> processList, Clock clock){
        this.processList = processList;
        this.processList.sort(new ByTimeOfArrival());
        numberOfProcesses = processList.size();
        this.clock = clock;
    }

    @Override
    public void update() {

        if (processList.size() > 0 || arrivedProcesses.size() > 0){

            if (processList.size() > 0) {
                while (processList.get(0).getTimeOfArrival() < clock.getTime()) {
                    arrivedProcesses.add(processList.get(0));
                    processList.remove(processList.get(0));
                    arrivedProcesses.sort(new ByRemainingWorkTime());
                    if (processList.size() == 0){
                        break;
                    }
                }
            }

            if (arrivedProcesses.size() > 0 ){
                currentProcess = arrivedProcesses.get(0);
            }

            if (currentProcess != null){

                currentProcess.setRemainingTime(currentProcess.getRemainingTime() - 1);

                if (currentProcess.getRemainingTime() == 0) {
                    totalWaitTime += (clock.getTime() - currentProcess.getProcessingTime() - currentProcess.getTimeOfArrival());
                    arrivedProcesses.remove(currentProcess);
                    currentProcess = null;
                }
            }
        }else {
            System.out.println("SRJF: " + totalWaitTime / numberOfProcesses);
             clock.stopClock();

        }

    }
}
