package com.company;

import java.util.ArrayList;

public class CSCAN1 {

    private final ArrayList<Request> requests = new ArrayList<>();
    private final int[] arrivedRequests;

    private int timer = 0;

    private int counter = 0;

    public void addRequest(Request request) {
        requests.add(request);
    }

    public CSCAN1(int numberOfCylinders) {
        arrivedRequests = new int[numberOfCylinders + 1];
    }

    public int run() {

        requests.sort(new ByTimeOfArrival());

        int countdown = requests.size();
        int arrivedCountdown = 0;
        int currentPos = 0;
        if (requests.size() > 0) {
            currentPos = requests.get(0).getCylinderNumber();
        }

        while (countdown > 0) {

            while (requests.size() > 0 && requests.get(0).getTimeOfArrival() <= timer) {

                arrivedCountdown++;
                arrivedRequests[requests.get(0).getCylinderNumber()] ++;
                requests.remove(0);
            }



            if (currentPos < arrivedRequests.length){
                if (arrivedRequests[currentPos] > 0){
                    countdown -= arrivedRequests[currentPos];
                    arrivedCountdown-= arrivedRequests[currentPos];
                    arrivedRequests[currentPos] = 0;
                }
                if (arrivedCountdown > 0){
                    counter++;
                    currentPos++;
                }
            }else {
                currentPos = 0;
            }

            timer ++;
        }

        return counter;
    }
}
