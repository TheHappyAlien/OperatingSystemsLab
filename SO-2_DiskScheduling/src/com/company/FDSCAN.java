package com.company;

import java.util.ArrayList;

public class FDSCAN {

    private final ArrayList<Request> requests = new ArrayList<>();
    private final ArrayList<RealTimeRequest> realTimeRequests = new ArrayList<>();

    private final int[] arrivedRequests;

    private int timer = 0;

    private int counter = 0;

    private int timeoutCounter = 0;

    public void addRequest(Request request) {
        requests.add(request);
    }

    public void addRTRequest(RealTimeRequest realTimeRequest) {
        realTimeRequests.add(realTimeRequest);
    }

    public FDSCAN(int numberOfCylinders) {
        arrivedRequests = new int[numberOfCylinders + 1];
    }

    public int run() {

        boolean toUpper = true;

        requests.sort(new ByTimeOfArrival());

        realTimeRequests.sort(new ByTimeOfArrival());

        int countdown = requests.size() + realTimeRequests.size();
        int arrivedCountdown = 0;
        int currentPos = 0;
        if (requests.size() > 0) {
            currentPos = requests.get(0).getCylinderNumber();
        }

        while (countdown > 0) {

            while (requests.size() > 0 && requests.get(0).getTimeOfArrival() <= timer) {

                arrivedCountdown++;
                arrivedRequests[requests.get(0).getCylinderNumber()]++;
                requests.remove(0);

            }

            while (realTimeRequests.size() > 0 && realTimeRequests.get(0).getTimeOfArrival() <= timer) {

                arrivedCountdown++;
                RealTimeRequest currentRTR = realTimeRequests.get(0);
                realTimeRequests.remove(0);

//region
//                if (currentRTR != null && currentRTR.getExecutionDeadline() > realTimeRequests.get(0).getExecutionDeadline()){
//                    arrivedCountdown++;
//                    sideList.add(currentRTR);
//                    sideList.sort(new ByDeadline());
//                    currentRTR = realTimeRequests.get(0);
//                    realTimeRequests.remove(0);
//                }else if (currentRTR == null){
//                    arrivedCountdown++;
//                    currentRTR = realTimeRequests.get(0);
//                    realTimeRequests.remove(0);
//                }

//                if (sideList.size() > 0 && sideList.get(0).getExecutionDeadline() < currentRTR.getExecutionDeadline()){
//                    sideList.add(currentRTR);
//                    currentRTR = sideList.get(0);
//                    sideList.remove(0);
//                    sideList.sort(new ByDeadline());
//                }else if (realTimeRequests.size() <= 0){
//                    realTimeRequests.add(sideList.get(0));
//                    sideList.remove(0);
//                }
//
//                ArrayList<RealTimeRequest> toRemove = new ArrayList<>();
//
//                for (RealTimeRequest realTimeRequest : sideList){
//                    if (timer + Math.abs(currentPos - realTimeRequest.getCylinderNumber()) > realTimeRequest.getExecutionDeadline()){
//                        toRemove.add(realTimeRequest);
//                        arrivedRequests[realTimeRequest.getCylinderNumber()] ++;
//                    }else if (currentPos == realTimeRequest.getCylinderNumber()){
//                        toRemove.add(realTimeRequest);
//                        arrivedCountdown--;
//                        countdown--;
//                    }
//                }
//
//                sideList.removeAll(toRemove);
//endregion

                if (timer + Math.abs(currentPos - currentRTR.getCylinderNumber()) < currentRTR.getExecutionDeadline()) {

                    ArrayList<RealTimeRequest> toRemove = new ArrayList<>();

                    if (currentPos > currentRTR.getCylinderNumber()) {
                        toUpper = false;
                    }

                    while (currentPos != currentRTR.getCylinderNumber()) {

                        if (currentPos < currentRTR.getCylinderNumber()) {
                            toUpper = true;
                            if (arrivedRequests[currentPos] > 0) {
                                countdown -= arrivedRequests[currentPos];
                                arrivedCountdown -= arrivedRequests[currentPos];
                                arrivedRequests[currentPos] = 0;
                            }
                            currentPos++;
                            counter++;
                            timer++;
                        }

                        if (!toUpper && currentPos > currentRTR.getCylinderNumber()) {
                            if (arrivedRequests[currentPos] > 0) {
                                countdown -= arrivedRequests[currentPos];
                                arrivedCountdown -= arrivedRequests[currentPos];
                                arrivedRequests[currentPos] = 0;
                            }
                            currentPos--;
                            counter++;
                            timer++;
                        }

                        for (RealTimeRequest RTR : realTimeRequests) {
                            if (RTR.getTimeOfArrival() <= timer) {
                                if (toUpper && RTR.getCylinderNumber() >= currentPos && RTR.getCylinderNumber() <= currentRTR.getCylinderNumber()) {
                                    toRemove.add(RTR);
                                    countdown--;
                                } else if (!toUpper && RTR.getCylinderNumber() <= currentPos && RTR.getCylinderNumber() >= currentRTR.getCylinderNumber()) {
                                    toRemove.add(RTR);
                                    countdown--;
                                }
                            }
                        }
                        realTimeRequests.removeAll(toRemove);
                        toRemove.clear();

                    }

                } else {
                    arrivedCountdown++;
                    countdown++;
                    timeoutCounter++;
                    arrivedRequests[currentRTR.getCylinderNumber()]++;
                }
                arrivedCountdown--;
                countdown--;
            }


            if (toUpper && currentPos < arrivedRequests.length - 1) {
                if (arrivedRequests[currentPos] > 0) {
                    countdown -= arrivedRequests[currentPos];
                    arrivedCountdown -= arrivedRequests[currentPos];
                    arrivedRequests[currentPos] = 0;
                }
                if (arrivedCountdown > 0) {
                    currentPos++;
                    counter++;
                }
            } else {
                toUpper = false;
            }


            if (!toUpper && currentPos > 0) {
                if (arrivedRequests[currentPos] > 0) {
                    countdown -= arrivedRequests[currentPos];
                    arrivedCountdown -= arrivedRequests[currentPos];
                    arrivedRequests[currentPos] = 0;
                }
                if (arrivedCountdown > 0) {
                    currentPos--;
                    counter++;
                }
            } else {
                toUpper = true;
            }

            timer++;
        }

        return counter;
    }

    public int getTimeoutCounter(){
        return timeoutCounter;
    }

}
