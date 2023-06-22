package ru.guhar4k.catfeeder.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException("Неверное значение округления числа с плавающей точки: " + places);

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
