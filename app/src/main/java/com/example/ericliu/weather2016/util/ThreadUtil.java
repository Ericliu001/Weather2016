package com.example.ericliu.weather2016.util;

import java.util.Random;

/**
 * Created by ericliu on 12/04/2016.
 */
public final class ThreadUtil {
    private ThreadUtil() {
    }


    public static void sleepRandomLength() {
        Random ran = new Random();
        long sleepTime = ran.nextInt(5000);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
