package com.company;

import java.util.Comparator;

public class ByTimeOfArrival implements Comparator<Request> {
    @Override
    public int compare(Request o1, Request o2) {
        return Integer.compare(o1.getTimeOfArrival(), o2.getTimeOfArrival());
    }
}
