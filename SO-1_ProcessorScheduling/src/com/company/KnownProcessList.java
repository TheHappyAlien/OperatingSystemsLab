package com.company;

import java.util.ArrayList;

public class KnownProcessList {

    private ArrayList<ProcessSim> knownProcesses = new ArrayList<>();

    public KnownProcessList(){
        knownProcesses.add(new ProcessSim(0, 5));
        knownProcesses.add(new ProcessSim(1, 2));
        knownProcesses.add(new ProcessSim(2, 1));
        knownProcesses.add(new ProcessSim(3, 3));
        knownProcesses.add(new ProcessSim(4, 4));

//        for (int i = 0; i < 2000; i++){
//            knownProcesses.add(new ProcessSim(i*2 + 1,200));
//        }

        ProcessSim.resetProcessNumber();

    }

    public ArrayList<ProcessSim> getKnownProcesses() {
        return knownProcesses;
    }
}
