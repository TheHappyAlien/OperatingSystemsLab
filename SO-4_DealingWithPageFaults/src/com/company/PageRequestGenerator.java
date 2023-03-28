package com.company;

import java.util.ArrayList;
import java.util.Random;

public class PageRequestGenerator {

    private final Random random = new Random();

    private ArrayList<ArrayList<Integer>> pageNumberMatrix;
    private int numberOfUniquePages;

    public ArrayList<Integer> generate(int numberOfRequests, int numberOfUniquePages, int maxLocalitySize, int maxPageNumber){
        this.numberOfUniquePages = numberOfUniquePages;
        ArrayList<Integer> requests = new ArrayList<>();
        pageNumberMatrix = new ArrayList<>();
        pageNumberMatrix.add(new ArrayList<Integer>());
        int localityIndex = 0;
        int uniqueSeedNumber = random.nextInt(maxPageNumber) + 1;;

        for (int i = 0; i < numberOfUniquePages; i++){
            int diceRoll = random.nextInt(10) + 1;

            if (diceRoll > 9 || pageNumberMatrix.get(localityIndex).size() >= maxLocalitySize) {
                uniqueSeedNumber = random.nextInt(maxPageNumber) + 1;
                if (pageNumberMatrix.get(localityIndex).size() > 0){
                    localityIndex++;
                    pageNumberMatrix.add(new ArrayList<Integer>());
                }
            }

            pageNumberMatrix.get(localityIndex).add(uniqueSeedNumber++);
        }

        int lastRequest = pageNumberMatrix.get(0).get(0);
        localityIndex = 0;


        for (int i = 0; i < numberOfRequests; i++ ){
            int diceRoll = random.nextInt(100) + 1;

            if (diceRoll <= 70){
                requests.add(lastRequest);
            }else if (diceRoll <= 90){
                lastRequest = pageNumberMatrix.get(localityIndex).get(random.nextInt(pageNumberMatrix.get(localityIndex).size()));
                requests.add(lastRequest);
            }else {
                localityIndex = random.nextInt(pageNumberMatrix.size());
                lastRequest = pageNumberMatrix.get(localityIndex).get(random.nextInt(pageNumberMatrix.get(localityIndex).size()));

                requests.add(lastRequest);
            }
        }
        return requests;
    }

    public ArrayList<ArrayList<Integer>> getPageNumberMatrix() {
        return pageNumberMatrix;
    }

    public int getNumberOfUniquePages() {
        return numberOfUniquePages;
    }
}

