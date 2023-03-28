package com.company;

import java.util.ArrayList;

public class FIFO extends Algorithm {

    public FIFO(int numberOfMemorySlots, ArrayList<Integer> pageRequests) {
        super(numberOfMemorySlots, pageRequests);
    }

    @Override
    public int run(){

        int swapIndex = 0;

        while (pageRequests.size() > 0){
            if (memory.process(pageRequests.get(0), swapIndex) == 1){
                swapIndex++;
            }

            if (swapIndex >= memory.getNumberOfSlots()){
                swapIndex = 0;
            }

            pageRequests.remove(0);
        }

        return memory.getNumberOfPageFaults();
    }

}
