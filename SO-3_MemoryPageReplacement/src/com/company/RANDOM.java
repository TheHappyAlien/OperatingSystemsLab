package com.company;

import java.util.ArrayList;
import java.util.Random;

public class RANDOM extends Algorithm {

    public RANDOM(int numberOfMemorySlots, ArrayList<Integer> pageRequests) {
        super(numberOfMemorySlots, pageRequests);
    }

    @Override
    public int run() {
        boolean hasEmptySlots = true;
        int swapIndex = 0;

        while (pageRequests.size() > 0){
            if (hasEmptySlots && memory.process(pageRequests.get(0), swapIndex) == 1) {
                swapIndex++;
                if (swapIndex >= memory.getNumberOfSlots()) {
                    hasEmptySlots = false;
                }
            }else {
                memory.process(pageRequests.get(0), new Random().nextInt(memory.getNumberOfSlots()));
            }

            pageRequests.remove(0);
        }

        return memory.getNumberOfPageFaults();
    }
}
