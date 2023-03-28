package com.company;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

public class PhysicalMemory {

    private int numberOfSlots;
    private boolean hasEmptySlots = false;
    private int numberOfUsedSlots = 0;
    private int[] memory;
    private int numberOfPageFaults;

    public PhysicalMemory(int numberOfSlots){
        this.numberOfSlots = numberOfSlots;
        memory = new int[numberOfSlots];
        if (numberOfSlots > 0) hasEmptySlots = true;
    }

    public boolean process(int pageNumber, int swapIndex){
        if (numberOfSlots > 0) {

            for (Integer page : memory) {
                if (pageNumber == page) {
                    return false;
                }
            }
            memory[swapIndex] = pageNumber;
        }

        numberOfPageFaults++;
        if(numberOfUsedSlots >= numberOfSlots){
            hasEmptySlots = false;
        }else numberOfUsedSlots++;
        return true;
    }

    public boolean checkPageFault(int pageNumber){
        if (numberOfSlots <= 0){
            numberOfPageFaults++;
            return true;
        }

        for (Integer page:memory){
            if (pageNumber == page){
                return false;
            }
        }

        return true;
    }

    public void swapPages(int pageNumber, int swapIndex){
        numberOfPageFaults++;
        memory[swapIndex] = pageNumber;
    }

    public void changeNumberOfSlots(int newNumberOfSlots){
        if (newNumberOfSlots >= 0) {
            if (numberOfSlots == newNumberOfSlots) return;
            int[] newMemory = new int[newNumberOfSlots];

            if (newNumberOfSlots > numberOfSlots) {
                if (numberOfSlots >= 0) System.arraycopy(memory, 0, newMemory, 0, numberOfSlots);
                hasEmptySlots = true;
            } else System.arraycopy(memory, 0, newMemory, 0, newNumberOfSlots);
            numberOfSlots = newNumberOfSlots;
            memory = newMemory;
        }
        else throw new IllegalArgumentException("Number of slots can't be negative.");
    }

    public int[] getMemory(){
        return memory;
    }

    public int getNumberOfSlots(){
        return numberOfSlots;
    }

    public int getNumberOfPageFaults(){
        return numberOfPageFaults;
    }

    public boolean hasEmptySlots() {
        return hasEmptySlots;
    }

    public void setNumberOfSlots(int numberOfSlots) {
        this.numberOfSlots = numberOfSlots;
    }
}
