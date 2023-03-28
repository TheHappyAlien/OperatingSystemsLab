package com.company;

import java.util.Comparator;

public class byArrivalTime implements Comparator<Process> {

    @Override
    public int compare(Process p1, Process p2) {
        return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());
    }
}
