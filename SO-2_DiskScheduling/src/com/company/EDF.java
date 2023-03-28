package com.company;

import java.util.ArrayList;

public class EDF {

    private final ArrayList<Request> requests = new ArrayList<>();

    private final ArrayList<Request> arrivedRequests = new ArrayList<>();

    private final ArrayList<RealTimeRequest> realTimeRequests = new ArrayList<>();

    private int counter = 0;
    private int timer = 0;

    private int timeoutCounter = 0;

    public void addRequest(Request request) {
        requests.add(request);
    }

    public void addRTRequest(RealTimeRequest realTimeRequest) {
        realTimeRequests.add(realTimeRequest);
    }

    public int getTimeoutCounter() {
        return timeoutCounter;
    }

    public int run() {

        Request prevRequest = null;

        realTimeRequests.sort(new ByDeadline());

        requests.sort(new ByTimeOfArrival());

        while (requests.size() > 0 || realTimeRequests.size() > 0 || arrivedRequests.size() > 0) {

            while (realTimeRequests.size() > 0 && realTimeRequests.get(0).getTimeOfArrival() <= timer) {
                arrivedRequests.add(0, realTimeRequests.get(0));
                realTimeRequests.remove(0);
            }

            while (requests.size() > 0 && requests.get(0).getTimeOfArrival() <= timer) {
                arrivedRequests.add(requests.get(0));
                requests.remove(0);
            }


            if (arrivedRequests.size() > 0) {

                if (prevRequest != null) {
                    if (arrivedRequests.get(0) instanceof RealTimeRequest) {
                        if (timer + Math.abs(arrivedRequests.get(0).getCylinderNumber() - prevRequest.getCylinderNumber()) > ((RealTimeRequest) arrivedRequests.get(0)).getExecutionDeadline()) {
                            timeoutCounter++;
                        }
                    }

                    counter += Math.abs(arrivedRequests.get(0).getCylinderNumber() - prevRequest.getCylinderNumber());
                    timer += Math.abs(arrivedRequests.get(0).getCylinderNumber() - prevRequest.getCylinderNumber());
                }

                prevRequest = arrivedRequests.get(0);

                ArrayList<Request> toRemove = new ArrayList<>();

                for (Request request : arrivedRequests) {
                    if (request.getCylinderNumber() == prevRequest.getCylinderNumber()) {
                        toRemove.add(request);
                    }
                }
                arrivedRequests.removeAll(toRemove);
            }

            timer++;

        }
        return counter;

    }
}



