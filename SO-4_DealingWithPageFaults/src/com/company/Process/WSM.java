package com.company.Process;


import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

public class WSM extends Process {


    private final LinkedList<Pair<Integer, Integer>> workingSet = new LinkedList<>();
    private int timer = 0;
    private final int maxWindow;

    public WSM(int numberOfMemorySlots, ArrayList<Integer> pageRequests, int numberOfUniquePages, int maxWindow) {
        super(numberOfMemorySlots, pageRequests, numberOfUniquePages);
        this.maxWindow = maxWindow;
    }

    @Override
    public boolean next() {

        if (pageRequests.size() <= 0) {
            isDone = true;
        } else {
            timer++;

            if (memory.checkPageFault(pageRequests.get(0))) {
                slotRequestPointer = 0;
                if (hasAvailableSlots && workingSet.size() < maxWindow) {
                    slotRequestPointer = 1;
                    memory.changeNumberOfSlots(memory.getNumberOfSlots() + 1);
                    memory.swapPages(pageRequests.getFirst(), memory.getNumberOfSlots() - 1);
                    workingSet.addFirst(new Pair<>(pageRequests.getFirst(), timer));
                } else if (memory.getNumberOfSlots() > 0) {
                    int swapIndex = 0;
                    for (Integer page : memory.getMemory()) {
                        if (page.equals(workingSet.getLast().getKey())) {
                            break;
                        }
                        swapIndex++;
                    }

                    memory.swapPages(pageRequests.get(0), swapIndex);
                    workingSet.removeLast();
                    workingSet.addFirst(new Pair<>(pageRequests.getFirst(), timer));
                }
                wasThereAPageFault = true;
            } else {

                ListIterator<Pair<Integer, Integer>> iterator = workingSet.listIterator();
                while (iterator.hasNext()) {
                    if (iterator.next().getKey().equals(pageRequests.get(0))) {
                        iterator.remove();
                        break;
                    }
                }
                workingSet.addFirst(new Pair<>(pageRequests.get(0), timer));
                slotRequestPointer = 0;

                if ((workingSet.getLast().getValue()) < timer - maxWindow) {
                    int pageGettingRemoved = workingSet.getLast().getKey();
                    workingSet.removeLast();
                    int swapIndex = 0;
                    for (Integer page : memory.getMemory()) {
                        if (page == pageGettingRemoved) {
                            break;
                        }
                        swapIndex++;
                    }

                    memory.getMemory()[swapIndex] = memory.getMemory()[memory.getNumberOfSlots() - 1];
                    memory.changeNumberOfSlots(memory.getNumberOfSlots() - 1);
                    slotRequestPointer = -1;

                }

                wasThereAPageFault = false;
            }

            if (memory.getNumberOfSlots() > 0) {
                pageRequests.remove(0);
            }

            return wasThereAPageFault;

        }


        return false;
    }

}
