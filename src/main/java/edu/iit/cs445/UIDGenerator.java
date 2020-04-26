package edu.iit.cs445;

import java.util.concurrent.atomic.AtomicInteger;

public class UIDGenerator {
    private static AtomicInteger atomicInteger = new AtomicInteger();
    public static String getUID(){
        return Integer.toString(atomicInteger.incrementAndGet());
    }
}
