package com.company;

import java.util.*;

public class ProbeXTimes {

    private final ArrayList<Processor> processors = new ArrayList<>();
    private final Random random = new Random();

    public double run(int numberOfProcessors, List<Process> processList, int x, int maxUsage) {
        float[] processorUsage = new float[numberOfProcessors];
        LinkedList<Process> processes = new LinkedList<>(processList);
        int numberOfProcesses = processList.size();
        int sizeOfProcessQueue = numberOfProcesses;
        double sumUsage = 0;
        int timer = 0;
        processes.sort(new byArrivalTime());

        for (int i = 0; i < numberOfProcessors; i++) {
            processors.add(new Processor());
        }


        while (numberOfProcesses > 0) {
            timer++;

            Process currentProcess;
            if (sizeOfProcessQueue > 0 && (currentProcess = processes.getFirst()).getArrivalTime() < timer) {

                boolean foundProcess = false;
                Processor chosenProcessor = processors.get(random.nextInt(numberOfProcessors));

                if (chosenProcessor.getCurrentUsage() >= maxUsage) {

                    for (int trialNumber = 0; trialNumber < x; trialNumber++) {
                        Processor trialProcessor = processors.get(random.nextInt(numberOfProcessors));

                        if (trialProcessor.getCurrentUsage() < maxUsage && (trialProcessor.getCurrentUsage() + currentProcess.getRequiredPower()) <= 100) {
                            foundProcess = true;
                            trialProcessor.addProcess(currentProcess);
                            processes.removeFirst();
                            sizeOfProcessQueue--;
                            break;
                        }

                    }
                }

                if (!foundProcess && (chosenProcessor.getCurrentUsage() + currentProcess.getRequiredPower()) <= 100) {
                    chosenProcessor.addProcess(currentProcess);
                    processes.removeFirst();
                    sizeOfProcessQueue--;
                }
            }

            for (Processor processor : processors) {
                if (processor.getNumberOfProcesses() > 0) {
                    if (!processor.clockTick()) {
                        numberOfProcesses--;
                    }
                    sumUsage += processor.getCurrentUsage();
                }
            }

            for (int i = 0; i < numberOfProcessors; i++){
                if (processors.get(i).getNumberOfProcesses() > 0) {
                    processorUsage[i] += processors.get(i).getCurrentUsage();
                }
            }

        }

        float maxProcessorUsage = 0;
        float minProcessorUsage = Float.POSITIVE_INFINITY;

        for (float usage : processorUsage){
            if (usage > maxProcessorUsage){maxProcessorUsage = usage;}
            if (usage < minProcessorUsage){minProcessorUsage = usage;}
        }

        maxProcessorUsage = maxProcessorUsage/timer;
        minProcessorUsage = minProcessorUsage/timer;

        System.out.println("Probe x times:");
        System.out.printf("Max avg usage: %2.2f\n", maxProcessorUsage);
        System.out.printf("Min avg usage: %2.2f\n", minProcessorUsage);


        return sumUsage / (timer * numberOfProcessors);
    }

}
