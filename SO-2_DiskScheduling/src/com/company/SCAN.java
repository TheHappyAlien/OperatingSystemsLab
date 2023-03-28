package com.company;

import java.util.ArrayList;

public class SCAN {

    private final ArrayList<Request> requests = new ArrayList<>();

    private int timer = 0;

    private int counter = 0;

    private final int numberOfCylinders;

    private boolean finished;

    public void addRequest(Request request) {
        requests.add(request);
    }

    public SCAN(int numberOfCylinders) {
        this.numberOfCylinders = numberOfCylinders;
    }

    public void run() {

        ArrayList<Request> arrivedRequests = new ArrayList<>();

        Request currentRequest = null;

        boolean toUpper = true;

        requests.sort(new ByTimeOfArrival());

        while (requests.size() > 0 || arrivedRequests.size() > 0) {


            while (requests.size() > 0 && requests.get(0).getTimeOfArrival() <= timer) {

                arrivedRequests.add(requests.get(0));
                requests.remove(0);

            }

            if (arrivedRequests.size() > 0) {

                int shortestRoute = 0;

                ArrayList<Request> toRemove = new ArrayList<>();

                if (currentRequest != null) {

                    boolean found = false;

                    int currentPos = currentRequest.getCylinderNumber();

                    currentRequest = arrivedRequests.get(0);

                    if (toUpper) {
                        shortestRoute = numberOfCylinders - currentPos;
                    }else{
                        shortestRoute = currentPos;
                    }

                    for (Request arrivedRequest : arrivedRequests) {

                        if (toUpper) {
                            if (currentPos - arrivedRequest.getCylinderNumber() <= 0) {

                                if (arrivedRequest.getCylinderNumber() == currentPos){
                                    toRemove.add(arrivedRequest);
                                }
                                else if (Math.abs(currentPos - arrivedRequest.getCylinderNumber()) < shortestRoute) {
                                    currentRequest = arrivedRequest;
                                    shortestRoute = Math.abs(currentPos - currentRequest.getCylinderNumber());
                                    toRemove.clear();
                                }

                                if (currentRequest.getCylinderNumber() == numberOfCylinders){
                                    toUpper = false;
                                }

                                found = true;
                            }
                        } else {
                            if (currentPos - arrivedRequest.getCylinderNumber() >= 0) {

                                if (arrivedRequest.getCylinderNumber() == currentPos){
                                    toRemove.add(arrivedRequest);
                                }
                                else if (Math.abs(currentPos - arrivedRequest.getCylinderNumber()) < shortestRoute) {
                                    currentRequest = arrivedRequest;
                                    shortestRoute = Math.abs(currentPos - currentRequest.getCylinderNumber());
                                    toRemove.clear();
                                }

                                if (currentRequest.getCylinderNumber() == 0){
                                    toUpper = true;
                                }

                                found = true;
                            }
                        }

                    }

                    for (Request request : toRemove){
                        arrivedRequests.remove(request);
                    }

                    if (!found) {
                        counter --;
                        if (toUpper) {
                            currentRequest = new Request(0, numberOfCylinders + 1);
                        }else {
                            currentRequest = new Request(0, -1);
                        }

                        toUpper = !toUpper;
                    }

                } else {
                    currentRequest = arrivedRequests.get(0);
                }

                arrivedRequests.remove(currentRequest);

                counter += shortestRoute;
                timer += shortestRoute;
            }
            timer ++;
        }

        System.out.println(counter);

    }
}
