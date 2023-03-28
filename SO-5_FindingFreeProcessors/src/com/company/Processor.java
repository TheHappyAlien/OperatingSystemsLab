package com.company;

import java.util.LinkedList;


public class Processor {

    private final LinkedList<Process> processQueue = new LinkedList<>();
    private int numberOfProcesses = 0;
    private int currentUsage = 0;

    public Process removeProcess() {
        currentUsage -= processQueue.getFirst().getRequiredPower();
        numberOfProcesses--;
        return processQueue.removeFirst();
    }

    public Process getCurrentProcess(){
        return processQueue.getFirst();
    }

    public boolean clockTick() {
        Process currentProcess = processQueue.getFirst();
        currentProcess.setLength(currentProcess.getLength() - 1);

        if (currentProcess.getLength() > 0) {
            return true;
        }

        removeProcess();

        return false;
    }

    public void addProcess(Process process) {
        processQueue.addLast(process);
        currentUsage += process.getRequiredPower();
        numberOfProcesses++;
    }

    public LinkedList<Process> getProcessQueue() {
        return processQueue;
    }

    public int getCurrentUsage() {
        return currentUsage;
    }

    public int getNumberOfProcesses() {
        return numberOfProcesses;
    }
}
