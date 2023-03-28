package com.company;

import java.util.ArrayList;

public class Clock extends Thread{

    private Algorythm algorythm;

    private boolean keepGoing = true;
    private int time = 0;

    @Override
    public void run() {

        while (keepGoing){

            time++;

            algorythm.update();

        }

    }

    public int getTime(){
        return time;
    }

    public void stopClock(){
        keepGoing = false;
    }

    public void setAlgorithm(Algorythm algorythm){
        this.algorythm = algorythm;
    }

}
