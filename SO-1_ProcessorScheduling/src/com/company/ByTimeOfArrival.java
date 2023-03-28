package com.company;

import java.util.Comparator;

public class ByTimeOfArrival implements Comparator<ProcessSim> {
    @Override
    public int compare(ProcessSim p1, ProcessSim p2) {
        if (p1.getTimeOfArrival() == p2.getTimeOfArrival()){
            return Integer.compare(p1.getAssignedNumber(), p2.getAssignedNumber());
        } else return Integer.compare(p1.getTimeOfArrival(), p2.getTimeOfArrival());
    }
}
