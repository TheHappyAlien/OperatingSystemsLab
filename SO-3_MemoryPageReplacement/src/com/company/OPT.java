package com.company;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class OPT extends Algorithm {


    public OPT(int numberOfMemorySlots, ArrayList<Integer> pageRequests) {
        super(numberOfMemorySlots, pageRequests);
    }

    @Override
    public int run() {

        boolean hasEmptySlots = true;
        int swapIndex = 0;

        while (pageRequests.size() > 0){

            //As long as there are empty slots in memory, we put pages that throw pageFault there.
            if (hasEmptySlots && memory.process(pageRequests.get(0), swapIndex) == 1){
                swapIndex++;
                if (swapIndex >= memory.getNumberOfSlots()){
                    hasEmptySlots = false;
                }
            }else if (memory.checkPageFault(pageRequests.get(0)) == 1){

                //How many potential pages are there to remove (at first all pages are potentially to be removed)
                int leftPotential = memory.getNumberOfSlots();

                LinkedList<Integer> potentialSwaps = new LinkedList<>();

                for (Integer page:memory.getMemory()){
                    potentialSwaps.add(page);
                }


                //Checking which page will be requested last.
                Iterator<Integer> innerIterator = pageRequests.iterator();

                while (innerIterator.hasNext() && leftPotential > 1){

                    int nextPageRequest = innerIterator.next();

                    LinkedList<Integer> toRemove = new LinkedList<>();

                    for (Integer potentialSwap: potentialSwaps) {
                        if (potentialSwap == nextPageRequest){
                            toRemove.add(potentialSwap);
                            leftPotential--;
                        }
                        if (leftPotential <= 1){
                            break;
                        }
                    }

                    //Removing all pages that we run into while checking the list from potential swaps, except for the last one (if there is a last one).
                    potentialSwaps.removeAll(toRemove);
                }

                // Getting the index of the page number that we want to remove from physical memory.
                int index = 0;

                for (Integer page:memory.getMemory()){
                    if (potentialSwaps.get(0).equals(page)){
                        break;
                    }
                    index++;
                }

                memory.swapPages(pageRequests.get(0), index);

            }


            pageRequests.remove(0);

        }


        return memory.getNumberOfPageFaults();
    }

}
