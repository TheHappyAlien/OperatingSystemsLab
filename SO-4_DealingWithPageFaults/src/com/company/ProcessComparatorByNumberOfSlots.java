package com.company;

import com.company.Process.Process;

import java.util.Comparator;

public class ProcessComparatorByNumberOfSlots implements Comparator<Process> {

    @Override
    public int compare(Process p1, Process p2) {
        return Integer.compare(p1.getNumberOfUniquePages(), p2.getNumberOfUniquePages());
    }
}
