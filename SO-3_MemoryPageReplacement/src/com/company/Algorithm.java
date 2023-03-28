package com.company;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Algorithm {

    protected final PhysicalMemory memory;
    protected final LinkedList<Integer> pageRequests = new LinkedList<>();

    public Algorithm(int numberOfMemorySlots, ArrayList<Integer> pageRequests){
        this.memory = new PhysicalMemory(numberOfMemorySlots);
        this.pageRequests.addAll(pageRequests);
    }

    public abstract int run();

}
