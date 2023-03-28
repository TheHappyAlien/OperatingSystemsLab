package com.company;

import com.company.Process.LRU;
import com.company.Process.Process;
import com.company.Process.WSM;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        int numberOfRepetitions = 10;

        int numberOfProcesses = 10;
        int cpuSize = 50;
        int maxNumberOfRequests = 100000;
        int maxNumberOfUniquePages = 50;
        int maxLocalitySize = 5;
        int maxPageNumber = 2000;

        float maxFaultFrequency = 0.3f;
        float minFaultFrequency = 0.1f;

        int maxWindow = 2000;

        showcase(numberOfProcesses, cpuSize, numberOfRepetitions, maxNumberOfRequests, maxNumberOfUniquePages, maxLocalitySize, maxPageNumber, maxFaultFrequency, minFaultFrequency, maxWindow);
    }

    private static void showcase(int numberOfProcesses, int cpuSize, int numberOfRepetitions,  int maxNumberOfRequests, int maxNumberOfUniquePages,
                                 int maxLocalitySize, int maxPageNumber, float maxFaultFrequency, float minFaultFrequency, int maxWindow){
        PageRequestGenerator generator = new PageRequestGenerator();
        Random random = new Random();
        for (int i = 0; i < 3; i ++){
            double sumPageFaults = 0;
            for (int k = 0; k < numberOfRepetitions; k++) {
                List<Process> processList = new ArrayList<>();
                for (int j = 0; j < numberOfProcesses; j++) {
                    processList.add(new LRU(1, generator.generate(random.nextInt(maxNumberOfRequests), random.nextInt(maxNumberOfUniquePages) + 1, random.nextInt(maxLocalitySize) + 1, maxPageNumber), generator.getNumberOfUniquePages(), maxFaultFrequency, minFaultFrequency));
                }
                CPU cpu = new CPU(cpuSize, processList);
                switch (i){
                    case 0:
                        sumPageFaults += cpu.runEqual().get(0).getValue();
                        break;
                    case 1:
                        sumPageFaults += cpu.runProportional().get(0).getValue();
                        break;
                    case 2:
                        sumPageFaults += cpu.runBasedOnPageFaultFrequency().get(0).getValue();
                        break;
                    default:
                        break;
                }
            }

            switch (i){
                case 0:
                    System.out.println("Equal: " + sumPageFaults/numberOfRepetitions);
                    break;
                case 1:
                    System.out.println("Proportional: " + sumPageFaults/numberOfRepetitions);
                    break;
                case 2:
                    System.out.println("NumberOfPageFaults: " + sumPageFaults/numberOfRepetitions);
                    break;
                default:
                    break;
            }
        }
        double sumPageFaults = 0;
        for (int k = 0; k < numberOfRepetitions; k++) {
            List<Process> processList = new ArrayList<>();
            for (int j = 0; j < numberOfProcesses; j++) {
                processList.add(new WSM(0, generator.generate(random.nextInt(maxNumberOfRequests), random.nextInt(maxNumberOfUniquePages) + 1, random.nextInt(maxLocalitySize) + 1, maxPageNumber), generator.getNumberOfUniquePages(), maxWindow));
            }
            CPU cpu = new CPU(cpuSize, processList);
            sumPageFaults += cpu.runOnWorkingSet().get(0).getValue();
            }
        System.out.println("Working set: " + sumPageFaults/numberOfRepetitions);
    }

}
