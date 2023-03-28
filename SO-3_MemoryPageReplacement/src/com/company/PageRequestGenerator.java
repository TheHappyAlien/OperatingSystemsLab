package com.company;

import java.util.ArrayList;
import java.util.Random;

public class PageRequestGenerator {

    private static int lastRequest = 1;
    private static int lastSeedNumber = 1;

    public static ArrayList<Integer> generate(int numberOfRequests, int pageNumberBound){
        ArrayList<Integer> requests = new ArrayList<>();

        for (int i = 0; i < numberOfRequests; i++ ){
            int diceRoll = new Random().nextInt(100) + 1;

            if (diceRoll <= 90){
                requests.add(lastRequest);
            }else if (diceRoll <= 99){
                int rollNearPageNumber = new Random().nextInt(5) + 1;
                lastRequest = Math.min(Math.max(lastRequest + rollNearPageNumber - 3, 1), pageNumberBound);
                requests.add(lastRequest);
            }else {
                int comeBackAround = new Random().nextInt(3);
                if (comeBackAround == 1){
                    int rollNearPageNumber = new Random().nextInt(5) + 1;
                    lastRequest = Math.min(Math.max(lastSeedNumber + rollNearPageNumber - 3, 1), pageNumberBound);
                }else {
                    lastRequest = new Random().nextInt(pageNumberBound) + 1;
                }

                lastSeedNumber = lastRequest;
                requests.add(lastRequest);
            }

        }
        lastRequest = 1;
        return requests;
    }

    public static ArrayList<Integer> generate(int numberOfRequests, int pageNumberBound, int startingRequest, int samePageChance, int localPageChance){

        if (startingRequest <= 0){
            throw new IllegalArgumentException("Page number cannot be less than 1");
        }

        if (localPageChance >= 100){
            throw new IllegalArgumentException("Local request chance can't be 100% or more.");
        }

        ArrayList<Integer> requests = new ArrayList<>();
        lastRequest = startingRequest;


        for (int i = 0; i < numberOfRequests; i++ ){
            int diceRoll = new Random().nextInt(100) + 1;

            if (diceRoll < samePageChance){
                requests.add(lastRequest);
            }else if (diceRoll < localPageChance){
                int rollNearPageNumber = new Random().nextInt(4) + 1;
                lastRequest = Math.min(Math.max(lastRequest + rollNearPageNumber - 2, 1), pageNumberBound);
                requests.add(lastRequest);
            }else {
                lastRequest = new Random().nextInt(pageNumberBound) + 1;
                requests.add(lastRequest);
            }

        }
        lastRequest = 1;
        return requests;
    }

}
