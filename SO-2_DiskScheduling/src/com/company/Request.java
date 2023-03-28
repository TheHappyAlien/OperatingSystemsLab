package com.company;

public class Request {

    private final int timeOfArrival;
    private final int cylinderNumber;

    public Request(int timeOfArrival, int cylinderNumber){

        this.timeOfArrival = timeOfArrival;
        this.cylinderNumber = cylinderNumber;

    }

    public Request(Request request){
        this.timeOfArrival = request.getTimeOfArrival();
        this.cylinderNumber = request.getCylinderNumber();
    }

    public int getCylinderNumber() {
        return cylinderNumber;
    }

    public int getTimeOfArrival() {
        return timeOfArrival;
    }

}
