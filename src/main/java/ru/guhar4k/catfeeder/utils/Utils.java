package ru.guhar4k.catfeeder.utils;

public class Utils {
    public static void sleepMicro(long microsecondsCount) {
        long delay = microsecondsCount * 1000;
        long start = System.nanoTime();
        while (System.nanoTime() - start < delay) ;
    }

    public static void sleepSecond(long seconds) {
        long delay = seconds * 1000;
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < delay) ;
    }
}
