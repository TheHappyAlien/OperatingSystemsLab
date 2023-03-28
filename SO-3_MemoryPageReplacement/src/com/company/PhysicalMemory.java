package com.company;

public class PhysicalMemory {

    private final int numberOfSlots;
    private int[] memory;
    private int numberOfPageFaults;

    public PhysicalMemory(int numberOfSlots){
        this.numberOfSlots = numberOfSlots;
        memory = new int[numberOfSlots];
    }

    public Byte process(int pageNumber, int swapIndex){

        for (Integer page:memory){
            if (pageNumber == page){
                return 0;
            }
        }

        numberOfPageFaults++;
        memory[swapIndex] = pageNumber;
        return 1;
    }

    public Byte checkPageFault(int pageNumber){

        for (Integer page:memory){
            if (pageNumber == page){
                return 0;
            }
        }

        return 1;
    }

    public void swapPages(int pageNumber, int swapIndex){
        numberOfPageFaults++;
        memory[swapIndex] = pageNumber;
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
}
