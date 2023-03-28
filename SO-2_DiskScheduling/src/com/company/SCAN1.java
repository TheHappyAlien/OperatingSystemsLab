package com.company;

import java.util.ArrayList;

public class SCAN1 {


    private final ArrayList<Request> requests = new ArrayList<>();
    private final int[] arrivedRequests;

    private int timer = 0;

    private int counter = 0;

    public void addRequest(Request request) {
        requests.add(request);
    }

    public SCAN1(int numberOfCylinders) {
        arrivedRequests = new int[numberOfCylinders + 1];
    }

    public int run() {

        boolean toUpper = true;

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



            if (toUpper && currentPos < arrivedRequests.length - 1){
                if (arrivedRequests[currentPos] > 0){
                    countdown -= arrivedRequests[currentPos];
                    arrivedCountdown-= arrivedRequests[currentPos];
                    arrivedRequests[currentPos] = 0;
                }
                if (arrivedCountdown > 0){
                        currentPos++;
                        counter++;
                }
            }else {
                toUpper = false;
            }


            if (!toUpper && currentPos > 0){
                if (arrivedRequests[currentPos] > 0){
                    countdown -= arrivedRequests[currentPos];
                    arrivedCountdown-= arrivedRequests[currentPos];
                    arrivedRequests[currentPos] = 0;
                }
                if(arrivedCountdown > 0 ) {
                    currentPos--;
                    counter++;
                }
            }else {
                toUpper = true;
            }

            timer ++;
        }

        return counter;

    }
}
