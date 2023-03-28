package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main {

    private static final Random random = new Random();

    public static void main(String[] args){
/*        Clock clock = new Clock();
        Clock clock1 = new Clock();
        Clock clock2 = new Clock();
        Clock clock3 = new Clock();

        ArrayList<ProcessSim> processSims = new ArrayList<>();
        ArrayList<ProcessSim> processSims1 = new ArrayList<>();
        ArrayList<ProcessSim> processSims2 = new ArrayList<>();
        ArrayList<ProcessSim> processSims3 = new ArrayList<>();


        for (int i = 0; i < 1000; i++){

            ProcessSim processSim = new ProcessSim(random.nextInt(1000), random.nextInt(20) + 1);

            processSims.add(new ProcessSim(processSim));
            processSims1.add(new ProcessSim(processSim));
            processSims2.add(new ProcessSim(processSim));
            processSims3.add(new ProcessSim(processSim));

            ProcessSim processSim1 = new ProcessSim(random.nextInt(1000) + 1000, random.nextInt(200) + 200);

            processSims.add(new ProcessSim(processSim1));
            processSims1.add(new ProcessSim(processSim1));
            processSims2.add(new ProcessSim(processSim1));
            processSims3.add(new ProcessSim(processSim1));

        }

//        FCFS fcfs = new FCFS(new KnownProcessList().getKnownProcesses(), clock);
//        SJF sjf = new SJF(new KnownProcessList().getKnownProcesses(), clock1);
//        SRJF srjf = new SRJF(new KnownProcessList().getKnownProcesses(), clock2);
//        Rotation rotation = new Rotation(new KnownProcessList().getKnownProcesses(), clock3, 2);


        FCFS fcfs = new FCFS(processSims, clock);
        SJF sjf = new SJF(processSims1, clock1);
        SRJF srjf = new SRJF(processSims2, clock2);
        Rotation rotation = new Rotation(processSims3, clock3, 5);

        clock.setAlgorithm(fcfs);
        clock1.setAlgorithm(sjf);
        clock2.setAlgorithm(srjf);
        clock3.setAlgorithm(rotation);

        clock.start();
        clock1.start();
        clock2.start();
        clock3.start();*/



    }

}
