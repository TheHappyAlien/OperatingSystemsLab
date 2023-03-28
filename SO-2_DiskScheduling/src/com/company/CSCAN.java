package com.company;

import java.util.ArrayList;

public class CSCAN {
    private final ArrayList<Request> requests = new ArrayList<>();

    private int timer = 0;

    private int counter = 0;

    private final int numberOfCylinders;

    private boolean hitTheEnd = false;

    public void addRequest(Request request) {
        requests.add(request);
    }

    public CSCAN(int numberOfCylinders) {
        this.numberOfCylinders = numberOfCylinders;
    }

    public int run() {

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

                    shortestRoute = numberOfCylinders - currentPos;

                    for (Request arrivedRequest : arrivedRequests) {

                        if (currentPos - arrivedRequest.getCylinderNumber() <= 0) {

                            if (arrivedRequest.getCylinderNumber() == currentPos){
                                toRemove.add(arrivedRequest);
                            }
                            else if (Math.abs(currentPos - arrivedRequest.getCylinderNumber()) < shortestRoute) {
                                currentRequest = arrivedRequest;
                                shortestRoute = Math.abs(currentPos - currentRequest.getCylinderNumber());
                                toRemove.clear();
                            }

                            found = true;

                        }

                    }

                    for (Request request : toRemove){
                        arrivedRequests.remove(request);
                    }


                    if (!found) {
                        counter --;
                        currentRequest = new Request(0, -1);
                    }


                } else {
                    currentRequest = arrivedRequests.get(0);
                }

                arrivedRequests.remove(currentRequest);

                counter += shortestRoute;
                timer += shortestRoute;

            }

            timer++;
        }

        return counter;
    }


}
