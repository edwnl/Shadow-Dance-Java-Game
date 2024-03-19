package main.java.utils;

import java.util.Random;

public class Utils {

    /**
     * Returns a random number between the bounds.
     * Referenced from: <a href="https://stackoverflow.com/a/21204421">StackOverflow</a>
     */
    public static int getRandomNumber(int lowerBound, int upperBound) {
        Random random = new Random();
        return random.nextInt(upperBound - lowerBound) + lowerBound;
    }

    /**
     * Returns the squared distance between two 2D coordinates.
     */
    public static double squaredDistance(double x1, double y1, double x2, double y2) {
        return Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2);
    }
}
