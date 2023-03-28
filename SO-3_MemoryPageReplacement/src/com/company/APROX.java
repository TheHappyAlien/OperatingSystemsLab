package com.company;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class APROX extends Algorithm {

    LinkedList<Pair<Integer, Byte>> useOrder = new LinkedList<>();

    public APROX(int numberOfMemorySlots, ArrayList<Integer> pageRequests) {
        super(numberOfMemorySlots, pageRequests);
    }

    @Override
    public int run() {
        boolean hasEmptySlots = true;
        int swapIndex = 0;

        while (pageRequests.size() > 0) {

            //As long as there are empty slots in memory, we put pages that throw pageFault there.
            if (hasEmptySlots && memory.process(pageRequests.get(0), swapIndex) == 1) {
                useOrder.addLast(new Pair<Integer, Byte>(pageRequests.get(0), (byte) 1));
                swapIndex++;
                if (swapIndex >= memory.getNumberOfSlots()) {
                    hasEmptySlots = false;
                }
            } else if (memory.checkPageFault(pageRequests.get(0)) == 1) {

                int toRemove = -1;
                int removeIndex = 0;

                for (Pair<Integer, Byte> page : useOrder) {
                    if (page.getValue() == 0) {
                        toRemove = page.getKey();
                        break;
                    } else {
                        useOrder.set(removeIndex, new Pair<>(page.getKey(), (byte) 0));
                    }
                    removeIndex++;
                }

                if (toRemove == -1) {
                    toRemove = useOrder.get(0).getKey();
                    removeIndex = 0;
                }

                useOrder.remove(removeIndex);

                useOrder.addLast(new Pair<Integer, Byte>(pageRequests.get(0), (byte) 1));

                // Getting the index of the page number that we want to remove from physical memory.
                int index = 0;

                for (Integer page : memory.getMemory()) {
                    if (toRemove == page) {
                        break;
                    }
                    index++;
                }

                memory.swapPages(pageRequests.get(0), index);
            } else {
                int index = 0;
                for (Pair<Integer, Byte> page : useOrder) {
                    if (pageRequests.get(0).equals(page.getKey())) {
                        useOrder.set(index, new Pair<Integer, Byte>(page.getKey(), (byte) 1));
                        break;
                    }
                    index++;
                }
            }

            pageRequests.remove(0);
        }

        return memory.getNumberOfPageFaults();
    }
}

