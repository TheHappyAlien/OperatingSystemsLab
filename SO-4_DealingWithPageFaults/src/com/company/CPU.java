package com.company;

import com.company.Process.Process;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CPU {

    private final ArrayList<Process> cpu = new ArrayList<>();

    private int numberOfProcesses;
    private final int size;

    public CPU(int size, List<Process> listOfProcesses) {
        this.numberOfProcesses = listOfProcesses.size();
        this.size = size;

        int id = 1;
        for (Process process : listOfProcesses) {
            process.setProcessID(id++);
            cpu.add(process);
        }
    }

    public List<MarkedValue> runProportional() {
        setProportional();

        int totalPageFaults = 0;
        List<MarkedValue> summary = new ArrayList<>();
        Random random = new Random();
        Process currentProcess = cpu.get(0);

        while (numberOfProcesses > 0) {
            int diceRoll = random.nextInt(10) + 1;

            if (diceRoll == 10) {
                currentProcess = cpu.get(random.nextInt(numberOfProcesses));
            }

            currentProcess.next();

            if (currentProcess.isDone()) {
                totalPageFaults = totalPageFaults + currentProcess.getMemory().getNumberOfPageFaults();
                summary.add(new MarkedValue(currentProcess.getMemory().getNumberOfPageFaults(), currentProcess.getProcessID()));
                cpu.remove(currentProcess);
                setProportional();

                if (--numberOfProcesses > 0) {
                    currentProcess = cpu.get(random.nextInt(numberOfProcesses));
                }
            }
        }
        summary.add(0, new MarkedValue(totalPageFaults, -1));
        return summary;
    }

    public List<MarkedValue> runEqual() {
        setEqual();

        int totalPageFaults = 0;
        List<MarkedValue> summary = new ArrayList<>();
        Random random = new Random();
        Process currentProcess = cpu.get(0);

        while (numberOfProcesses > 0) {
            int diceRoll = random.nextInt(10) + 1;

            if (diceRoll == 10) {
                currentProcess = cpu.get(random.nextInt(numberOfProcesses));
            }

            currentProcess.next();

            if (currentProcess.isDone()) {
                summary.add(new MarkedValue(currentProcess.getMemory().getNumberOfPageFaults(), currentProcess.getProcessID()));
                totalPageFaults = totalPageFaults + currentProcess.getMemory().getNumberOfPageFaults();

                cpu.remove(currentProcess);
                setEqual();
                if (--numberOfProcesses > 0) {
                    currentProcess = cpu.get(random.nextInt(numberOfProcesses));
                }
            }
        }
        summary.add(0, new MarkedValue(totalPageFaults, -1));
        return summary;
    }

    public List<MarkedValue> runBasedOnPageFaultFrequency(){
        setProportional();

        int availableSlots = 0;

        int totalPageFaults = 0;
        List<MarkedValue> summary = new ArrayList<>();
        Random random = new Random();
        Process currentProcess = cpu.get(0);

        while (numberOfProcesses > 0){
            int diceRoll = random.nextInt(10) + 1;

            if (diceRoll == 10) {
                currentProcess = cpu.get(random.nextInt(numberOfProcesses));
            }

            currentProcess.next();
            if (currentProcess.slotRequestPointer == 1 && availableSlots > 0){
                currentProcess.getMemory().changeNumberOfSlots(currentProcess.getMemory().getNumberOfSlots() + 1);
                currentProcess.setSwapIndex(currentProcess.getMemory().getNumberOfSlots() - 1);
                availableSlots--;
            }else if (currentProcess.slotRequestPointer == -1 && currentProcess.getMemory().getNumberOfSlots() > 0){
                currentProcess.getMemory().changeNumberOfSlots(currentProcess.getMemory().getNumberOfSlots() - 1);
                availableSlots++;
            }


            if (currentProcess.isDone()) {
                availableSlots += currentProcess.getMemory().getNumberOfSlots();
                summary.add(new MarkedValue(currentProcess.getMemory().getNumberOfPageFaults(), currentProcess.getProcessID()));
                totalPageFaults = totalPageFaults + currentProcess.getMemory().getNumberOfPageFaults();

                cpu.remove(currentProcess);
                if (--numberOfProcesses > 0) {
                    currentProcess = cpu.get(random.nextInt(numberOfProcesses));
                }
            }
        }
        summary.add(0, new MarkedValue(totalPageFaults, -1));
        return summary;
    }

    public List<MarkedValue> runOnWorkingSet(){
        int availableSlots = size;

        int totalPageFaults = 0;
        List<MarkedValue> summary = new ArrayList<>();
        Random random = new Random();
        Process currentProcess = cpu.get(0);

        while (numberOfProcesses > 0) {
            int diceRoll = random.nextInt(10) + 1;

            if (diceRoll == 10) {
                currentProcess = cpu.get(random.nextInt(numberOfProcesses));
            }

            currentProcess.hasAvailableSlots = availableSlots > 0;

            currentProcess.next();

            if (currentProcess.slotRequestPointer == 1){
                availableSlots--;
            }else if (currentProcess.slotRequestPointer == -1){
                availableSlots++;
            }

            if (currentProcess.isDone()) {
                availableSlots += currentProcess.getMemory().getNumberOfSlots();
                summary.add(new MarkedValue(currentProcess.getMemory().getNumberOfPageFaults(), currentProcess.getProcessID()));
                totalPageFaults = totalPageFaults + currentProcess.getMemory().getNumberOfPageFaults();

                cpu.remove(currentProcess);
                if (--numberOfProcesses > 0) {
                    currentProcess = cpu.get(random.nextInt(numberOfProcesses));
                }
            }
        }
        summary.add(0, new MarkedValue(totalPageFaults, -1));
        return summary;
    }

    private void setEqual() {
        int leftOver = size % numberOfProcesses;
        int capacityPerProcess = (size - leftOver) / numberOfProcesses;

        for (Process process : cpu) {
            if (leftOver-- > 0) process.getMemory().changeNumberOfSlots(capacityPerProcess + 1);
            else  process.getMemory().changeNumberOfSlots(capacityPerProcess);
        }
    }

    private void setProportional() {
        int totalNumberOfProcessPages = 0;

        for (Process process : cpu) {
            totalNumberOfProcessPages += process.getNumberOfUniquePages();
        }

        double pageRatio = (double) size / totalNumberOfProcessPages;

        int numberOfUsedSlots = 0;
        for (Process process : cpu) {
            int slotsForCurrentProcess = (int) (pageRatio * process.getNumberOfUniquePages());
            numberOfUsedSlots += slotsForCurrentProcess;
            process.getMemory().changeNumberOfSlots(slotsForCurrentProcess);
        }

        int numberOfSlotsToFix = size - numberOfUsedSlots;
        if (numberOfSlotsToFix > 0) {

            cpu.sort(new ProcessComparatorByNumberOfSlots());

            int leftOver = numberOfSlotsToFix % numberOfProcesses;

            for (int i = 0; i < leftOver; i++) {
                Process process = cpu.get(i);
                process.getMemory().changeNumberOfSlots(process.getMemory().getNumberOfSlots() + 1);
                process.setSwapIndex(process.getMemory().getNumberOfSlots() - 1);
            }
        }
    }

}
