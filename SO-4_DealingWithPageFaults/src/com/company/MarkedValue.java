package com.company;

public class MarkedValue {

    private final int value;
    private int marker;

    public MarkedValue(int value){
        this.value = value;
    }

    public MarkedValue(int value, int marker) {
        this.value = value;
        this.marker = marker;
    }

    public void setMarker(int marker) {
        this.marker = marker;
    }

    public int getValue() {
        return value;
    }

    public int getMarker() {
        return marker;
    }

    @Override
    public String toString() {
        return "{value: " + value + ", process number: " + marker + "}";
    }
}
