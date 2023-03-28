package com.company;

import java.util.ArrayList;

public class FCFS {

    private final ArrayList<Request> requests = new ArrayList<>();

    private final ArrayList<Request> arrivedRequests = new ArrayList<>();

    private int counter = 0;
    private int timer = 0;

    public void addRequest(Request request) {
        requests.add(request);
    }

    public int run() {

        Request prevRequest = null;

        requests.sort(new ByTimeOfArrival());

        while (requests.size() > 0 || arrivedRequests.size() > 0) {

            while (requests.size() > 0 && requests.get(0).getTimeOfArrival() <= timer) {

                arrivedRequests.add(requests.get(0));
                requests.remove(0);
            }

            if (arrivedRequests.size() > 0) {
                if (prevRequest != null) {
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

