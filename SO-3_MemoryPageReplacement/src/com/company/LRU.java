package com.company;

import java.util.ArrayList;
import java.util.LinkedList;

public class LRU extends Algorithm{

    LinkedList<Integer> useOrder = new LinkedList<>();

    public LRU(int numberOfMemorySlots, ArrayList<Integer> pageRequests) {
        super(numberOfMemorySlots, pageRequests);
    }

    @Override
    public int run() {
        boolean hasEmptySlots = true;
        int swapIndex = 0;

        while (pageRequests.size() > 0) {

            //As long as there are empty slots in memory, we put pages that throw pageFault there.
            if (hasEmptySlots && memory.process(pageRequests.get(0), swapIndex) == 1) {
                useOrder.addLast(pageRequests.get(0));
                swapIndex++;
                if (swapIndex >= memory.getNumberOfSlots()) {
                    hasEmptySlots = false;
                }
            }else if (memory.checkPageFault(pageRequests.get(0)) == 1){
                int lastUsed = useOrder.removeFirst();
                useOrder.addLast(pageRequests.get(0));

                // Getting the index of the page number that we want to remove from physical memory.
                int index = 0;

                for (Integer page:memory.getMemory()){
                    if (lastUsed == page){
                        break;
                    }
                    index++;
                }

                memory.swapPages(pageRequests.get(0), index);
            }else {
                for (Integer page:useOrder){
                    if (pageRequests.get(0).equals(page)){
                        useOrder.remove(page);
                        useOrder.addLast(page);
                        break;
                    }
                }
            }

            pageRequests.remove(0);
        }

        return memory.getNumberOfPageFaults();
    }
}
