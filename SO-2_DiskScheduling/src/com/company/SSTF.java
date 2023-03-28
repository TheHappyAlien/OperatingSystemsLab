package com.company;

import java.util.ArrayList;

public class SSTF {

    private final ArrayList<Request> requests = new ArrayList<>();

    private int timer = 0;

    private int counter = 0;

    public void addRequest(Request request){
        requests.add(request);
    }

    public int run(){

        ArrayList<Request> arrivedRequests = new ArrayList<>();

        Request currentRequest = null;

        requests.sort(new ByTimeOfArrival());

        while (requests.size() > 0 || arrivedRequests.size() > 0){


            while (requests.size() > 0 && requests.get(0).getTimeOfArrival() <= timer) {

                arrivedRequests.add(requests.get(0));
                requests.remove(0);

            }

            if (arrivedRequests.size() > 0) {

                int shortestRoute = 0;

                if (currentRequest != null) {

                    int currentPos = currentRequest.getCylinderNumber();
                    currentRequest = arrivedRequests.get(0);

                    shortestRoute = Math.abs(currentPos - arrivedRequests.get(0).getCylinderNumber());

                    for (Request arrivedRequest : arrivedRequests) {

                        if (Math.abs(currentPos - arrivedRequest.getCylinderNumber()) < shortestRoute) {
                            currentRequest = arrivedRequest;
                            shortestRoute = Math.abs(currentPos - currentRequest.getCylinderNumber());
                        }

                        if (shortestRoute == 0){
                            break;
                        }
                    }

                }else {
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
