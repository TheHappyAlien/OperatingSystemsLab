package com.company;

public class RealTimeRequest extends Request{

    private int executionDeadline;

    public RealTimeRequest(int timeOfArrival, int cylinderNumber, int executionTimeout) {
        super(timeOfArrival, cylinderNumber);
        this.executionDeadline = executionTimeout + timeOfArrival;
    }

    public RealTimeRequest(RealTimeRequest realTimeRequest){
        super(realTimeRequest.getTimeOfArrival(), realTimeRequest.getCylinderNumber());
        this.executionDeadline = realTimeRequest.getExecutionDeadline();
    }

    public RealTimeRequest(Request request) {
        super(request);
    }

    public int getExecutionDeadline() {
        return executionDeadline;
    }
}
