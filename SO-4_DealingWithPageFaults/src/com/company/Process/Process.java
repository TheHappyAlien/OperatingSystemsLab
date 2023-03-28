package com.company.Process;

import com.company.PhysicalMemory;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Process {

    protected PhysicalMemory memory;
    protected final LinkedList<Integer> pageRequests = new LinkedList<>();
    private int processID;
    protected boolean isDone = false;
    protected int swapIndex = 0;
    private final int numberOfUniquePages;
    public int slotRequestPointer = 0;
    public boolean hasAvailableSlots;
    protected boolean wasThereAPageFault;

    public Process(int numberOfMemorySlots, ArrayList<Integer> pageRequests, int numberOfUniquePages){
        this.memory = new PhysicalMemory(numberOfMemorySlots);
        this.pageRequests.addAll(pageRequests);
        this.numberOfUniquePages = numberOfUniquePages;
    }

    public abstract boolean next();

    public int nextProcess(){return pageRequests.get(0);}

    public void setMemory(PhysicalMemory memory) {
        this.memory = memory;
    }

    public PhysicalMemory getMemory() {
        return memory;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    public int getProcessID() {
        return processID;
    }

    public boolean isDone() {
        return isDone;
    }

    public int getNumberOfUniquePages() {
        return numberOfUniquePages;
    }

    public void setSwapIndex(int swapIndex) {
        this.swapIndex = swapIndex;
    }
}
