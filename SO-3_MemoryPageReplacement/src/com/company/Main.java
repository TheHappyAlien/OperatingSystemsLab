package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ArrayList<Integer> pageRequests = PageRequestGenerator.generate(10000, 100);

        int memorySlots = 5;

        FIFO fifo = new FIFO(memorySlots, new ArrayList<>(pageRequests));
        LRU lru = new LRU(memorySlots, new ArrayList<>(pageRequests));
        APROX aprox = new APROX(memorySlots, new ArrayList<>(pageRequests));
        RANDOM random = new RANDOM(memorySlots, new ArrayList<>(pageRequests));
        OPT opt = new OPT(memorySlots, new ArrayList<>(pageRequests));

        int memorySlots1 = 20;

        FIFO fifo1 = new FIFO(memorySlots1, new ArrayList<>(pageRequests));
        LRU lru1 = new LRU(memorySlots1, new ArrayList<>(pageRequests));
        APROX aprox1 = new APROX(memorySlots1, new ArrayList<>(pageRequests));
        RANDOM random1 = new RANDOM(memorySlots1, new ArrayList<>(pageRequests));
        OPT opt1 = new OPT(memorySlots1, new ArrayList<>(pageRequests));

        System.out.println("FIFO: " + fifo.run());
        System.out.println("LRU: " + lru.run());
        System.out.println("APROX: " + aprox.run());
        System.out.println("RANDOM: " + random.run());
        System.out.println("OPT: " + opt.run());

        System.out.println("\n");

        System.out.println("FIFO: " + fifo1.run());
        System.out.println("LRU: " + lru1.run());
        System.out.println("APROX: " + aprox1.run());
        System.out.println("RANDOM: " + random1.run());
        System.out.println("OPT: " + opt1.run());
    }
}
