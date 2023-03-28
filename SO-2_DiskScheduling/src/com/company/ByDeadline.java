package com.company;

import java.util.Comparator;

public class ByDeadline implements Comparator<RealTimeRequest> {

    @Override
    public int compare(RealTimeRequest o1, RealTimeRequest o2) {
        return Integer.compare(o1.getExecutionDeadline(), o2.getExecutionDeadline());
    }
}
