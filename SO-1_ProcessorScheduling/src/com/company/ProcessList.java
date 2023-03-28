package com.company;

import java.util.ArrayList;

public class ProcessList {

    private ArrayList<ProcessSim> processList = new ArrayList<>();

    public void addProcess(ProcessSim processSim){
        processList.add(processSim);
    }

    public void clear(){
        processList.clear();
    }

    public ArrayList<ProcessSim> getProcessList(){
        return processList;
    }



}
