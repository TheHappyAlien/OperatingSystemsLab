package com.company;

import java.util.*;

public class TakeSome {


    private final ArrayList<Processor> processors = new ArrayList<>();
    private final LinkedList<Processor> processorsBelowMaxCapacity = new LinkedList<>();
    private final Random random = new Random();
    private int numberOfSwaps = 0;

    public double run(int numberOfProcessors, List<Process> processList, int maxUsage, int minUsage) {
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

        processorsBelowMaxCapacity.addAll(processors);

        while (numberOfProcesses > 0) {
            timer++;

            Process currentProcess;
            if (sizeOfProcessQueue > 0 && (currentProcess = processes.getFirst()).getArrivalTime() < timer) {

                boolean foundProcessor = false;
                Processor chosenProcessor = processors.get(random.nextInt(numberOfProcessors));

                if (chosenProcessor.getCurrentUsage() >= maxUsage) {
                    Processor trialProcessor = processors.get(random.nextInt(numberOfProcessors));

                    if (trialProcessor.getCurrentUsage() < maxUsage && (trialProcessor.getCurrentUsage() + currentProcess.getRequiredPower()) <= 100) {
                        foundProcessor = true;
                        trialProcessor.addProcess(currentProcess);
                        processes.removeFirst();
                        sizeOfProcessQueue--;
                    }
                }

                if (!foundProcessor && (chosenProcessor.getCurrentUsage() + currentProcess.getRequiredPower()) <= maxUsage) {
                    chosenProcessor.addProcess(currentProcess);
                    processes.removeFirst();
                    sizeOfProcessQueue--;
                }
            }

            Iterator<Processor> iterator = processorsBelowMaxCapacity.listIterator();
            while (iterator.hasNext()){
                Processor currentProcessorUnderMaxCap = iterator.next();
                Processor randomAskedProcessor;
                if ((randomAskedProcessor = processors.get(random.nextInt(numberOfProcessors))).getCurrentUsage() > maxUsage
                    && (currentProcessorUnderMaxCap.getCurrentUsage() + randomAskedProcessor.getCurrentProcess().getRequiredPower()) <= 100) {
                    currentProcessorUnderMaxCap.addProcess(randomAskedProcessor.removeProcess());
                    numberOfSwaps++;
                }

                if (currentProcessorUnderMaxCap.getCurrentUsage() >= maxUsage){
                    iterator.remove();
                }
            }

            for (Processor processor : processors) {
                if (processor.getNumberOfProcesses() > 0) {
                    if (!processor.clockTick()) {
                        numberOfProcesses--;
                    }
                    sumUsage += processor.getCurrentUsage();
                    if (processor.getCurrentUsage() < minUsage){
                        processorsBelowMaxCapacity.add(processor);
                    }
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

        System.out.println("Take some:");
        System.out.println("Number of swaps: " + numberOfSwaps);
        System.out.printf("Max avg usage: %2.2f\n", maxProcessorUsage);
        System.out.printf("Min avg usage: %2.2f\n", minProcessorUsage);

        return sumUsage / (timer * numberOfProcessors);
    }

}
