package com.company;

import java.util.Random;

public class RunnerClass {

    public void run(int numberOfCylinders, int numberOfRequests, int arrivalTimeBound, int repeatTimes, int numberOfRTRs, int RTRArrivalTimeBound, int executionTimeoutMin, int executionTimeoutMax){

        double FCFSCounter = 0;
        double SSTFCounter = 0;
        double SCANCounter = 0;
        double CSCANCounter = 0;

        double EDFCounter = 0;
        double FDSCANCounter = 0;

        double EDFRTimeoutCounter = 0;
        double FDSCANTimeoutCounter = 0;

        FCFS fcfs = new FCFS();
        SSTF sstf = new SSTF();
        SCAN1 scan1 = new SCAN1(numberOfCylinders);
        CSCAN1 cscan1 = new CSCAN1(numberOfCylinders);

        EDF edf = new EDF();
        FDSCAN fdscan = new FDSCAN(numberOfCylinders);

        Random random = new Random();

        for (int i = 0; i < repeatTimes; i++){

            FCFSCounter = 0;
            SSTFCounter = 0;
            SCANCounter = 0;
            CSCANCounter = 0;

            EDFCounter = 0;
            FDSCANCounter = 0;

            EDFRTimeoutCounter = 0;
            FDSCANTimeoutCounter = 0;

            for (int j = 0; j < numberOfRequests; j++){
                Request request = new Request(random.nextInt(arrivalTimeBound + 1), random.nextInt(numberOfCylinders + 1));

                fcfs.addRequest(new Request(request));
                sstf.addRequest(new Request(request));
                scan1.addRequest(new Request(request));
                cscan1.addRequest(new Request(request));

                edf.addRequest(new Request(request));
                fdscan.addRequest(new Request(request));
            }

            for (int k = 0; k < numberOfRTRs; k++){
                RealTimeRequest RTR = new RealTimeRequest(random.nextInt(RTRArrivalTimeBound + 1), random.nextInt(numberOfCylinders + 1), random.nextInt(executionTimeoutMax - executionTimeoutMin) + executionTimeoutMin);

                edf.addRTRequest(new RealTimeRequest(RTR));
                fdscan.addRTRequest(new RealTimeRequest(RTR));
            }

            FCFSCounter += fcfs.run();
            SSTFCounter += sstf.run();
            SCANCounter += scan1.run();
            CSCANCounter += cscan1.run();

            EDFCounter += edf.run();
            FDSCANCounter += fdscan.run();

            EDFRTimeoutCounter += edf.getTimeoutCounter();
            FDSCANTimeoutCounter += fdscan.getTimeoutCounter();

        }

        System.out.print("* FCFS * : ");
        System.out.printf("%.3f", FCFSCounter / repeatTimes);
        System.out.print("\n* SSTF * : ");
        System.out.printf("%.3f", SSTFCounter / repeatTimes);
        System.out.print("\n* SCAN * : ");
        System.out.printf("%.3f", SCANCounter / repeatTimes);
        System.out.print("\n* CSCAN * : ");
        System.out.printf("%.3f", CSCANCounter / repeatTimes);

//        if (numberOfRTRs > 0){
            System.out.println("\n");
            System.out.print("\n* EDF * : ");
            System.out.printf("%.3f", EDFCounter / repeatTimes);
            System.out.print("\nNumber of timeouts : ");
            System.out.printf("%.3f", EDFRTimeoutCounter / repeatTimes);

            System.out.print("\n\n* FDSCAN * : ");
            System.out.printf("%.3f", FDSCANCounter / repeatTimes);
            System.out.print("\nNumber of timeouts : ");
            System.out.printf("%.3f", FDSCANTimeoutCounter / repeatTimes);
//        }

    }
}
