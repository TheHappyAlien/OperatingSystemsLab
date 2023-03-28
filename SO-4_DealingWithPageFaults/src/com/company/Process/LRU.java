package com.company.Process;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class LRU extends Process {

    private final LinkedList<Integer> useOrder = new LinkedList<>();
    private int timer = 0;
    private final LinkedList<Integer> pageFaultLog = new LinkedList<>();
    private float pageFaultLogSize;
    private final float minPageFaultFrequency;
    private final float maxPageFaultFrequency;
    private int delay = 300;

    public LRU(int numberOfMemorySlots, ArrayList<Integer> pageRequests, int numberOfUniquePages, float maxPageFaultFrequency, float minPageFrequency) {
        super(numberOfMemorySlots, pageRequests, numberOfUniquePages);
        this.maxPageFaultFrequency = maxPageFaultFrequency;
        this.minPageFaultFrequency = minPageFrequency;
    }

    @Override
    public boolean next() {

        if (pageRequests.size() <= 0) {
            isDone = true;
        } else {

            timer++;
            delay++;

            //As long as there are empty slots in memory, we put pages that throw pageFault there.
            if (swapIndex < memory.getNumberOfSlots() && memory.process(pageRequests.get(0), swapIndex)) {
                useOrder.addLast(pageRequests.get(0));
                swapIndex++;
                wasThereAPageFault = true;
                pageFaultLog.addLast(timer);
                pageFaultLogSize++;
            } else if (memory.checkPageFault(pageRequests.get(0))) {
                if (useOrder.size() > 0 && memory.getNumberOfSlots() > 0) {
                    int lastUsed = useOrder.removeFirst();
                    useOrder.addLast(pageRequests.get(0));

                    // Getting the index of the page number that we want to remove from physical memory.
                    int index = 0;

                    for (int i = 0; i < memory.getMemory().length; i++) {

                        if (lastUsed == memory.getMemory()[i]) {
                            break;
                        }
                        index = i;
                    }

                    memory.swapPages(pageRequests.get(0), index);
                }
                wasThereAPageFault = true;
                pageFaultLog.addLast(timer);
                pageFaultLogSize++;
            } else {
                for (Integer page : useOrder) {
                    if (pageRequests.get(0).equals(page)) {
                        useOrder.remove(page);
                        useOrder.addLast(page);
                        break;
                    }
                }
                wasThereAPageFault = false;
            }

            if (!wasThereAPageFault) {
                pageRequests.remove(0);
            }

            if (pageFaultLog.size() > 0 && pageFaultLog.getFirst() <= timer - 1000) {
                pageFaultLog.removeFirst();
                pageFaultLogSize--;
            }

            slotRequestPointer = 0;

            if (delay >= 300) {

                if (timer < 1000) {
                    if ((timer / pageFaultLogSize) > maxPageFaultFrequency) slotRequestPointer = 1;
                    else if ((1000.0 / pageFaultLogSize) < minPageFaultFrequency) slotRequestPointer = -1;
                } else if ((1000.0 / pageFaultLogSize) > maxPageFaultFrequency) {
                    slotRequestPointer = 1;
                } else if ((1000.0 / pageFaultLogSize) < minPageFaultFrequency) {
                    slotRequestPointer = -1;
                }

                return wasThereAPageFault;
            }
            delay -= 300;
        }


        return false;
    }
}

