package com.company;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Main {
    private static LinkedList<Process>[] processes;
    public static void main(String[] args) {
	// write your code here

        ProbeXTimes probeXTimes = new ProbeXTimes();
        TryTillFound tryTillFound = new TryTillFound();
        TakeSome takeSome = new TakeSome();

        int numberOfProcessors = 4;
        int maxUsage = 70;
        int minUsage = 40;

        Random random = new Random();

        processes = new LinkedList[3];
        for (int i = 0; i < 3; i++){processes[i] = new LinkedList<Process>();}

        addProcesses(1, 15, 0, 1000, 40, 80, 50);

        addProcesses(5, 20, 0, 2000, 50, 100, 100);

        System.out.printf("Overall average usage: %2.2f\n\n", probeXTimes.run(numberOfProcessors, processes[1], 20, maxUsage));
        System.out.printf("Overall average usage: %2.2f\n\n", tryTillFound.run(numberOfProcessors, processes[0], maxUsage));
        System.out.printf("Overall average usage: %2.2f\n\n", takeSome.run(numberOfProcessors, processes[2], maxUsage, minUsage));

    }

    private static void addProcesses(int minRequiredPower, int maxRequiredPower, int minArrivalTime, int maxArrivalTime, int minLength, int maxLength, int numberOfProcesses){
        if (maxRequiredPower < minRequiredPower || maxArrivalTime < minArrivalTime || maxLength < minLength){
            throw new IllegalArgumentException("Top bound can't be less then bottom bound.");
        }

        Random random = new Random();
        int powerGap = maxRequiredPower - minRequiredPower + 1;
        int arrivalTimeGap = maxArrivalTime - minArrivalTime + 1;
        int lengthGap = maxLength - minLength + 1;

        for (int counter = 0; counter < numberOfProcesses; counter++) {
            Process newProcess = new Process(random.nextInt(powerGap) + minRequiredPower, random.nextInt(arrivalTimeGap) + minArrivalTime, random.nextInt(lengthGap) + minLength);

            for (int i = 0; i < 3; i++) {
                processes[i].add(new Process(newProcess));
            }
        }

    }
}
