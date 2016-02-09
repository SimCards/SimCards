package io.github.simcards.simcards.util;

import java.util.List;
import java.util.Random;

/**
 * Utility methods for generating random numbers.
 */
public class RandomUtil {

    /** The random number generator to use for generating random numbers. */
    private static Random sRandom = new Random(System.currentTimeMillis());

    /**
     * Gets a random number between a minimum (inclusive) and a maximum (exclusive)
     * @param min The minimum number that the random number can be.
     * @param max 1 + The maximum number that the random number can be.
     * @return A random number between the minimum and maximum numbers.
     */
    public static int getRandomNumberInRange(int min, int max) {
        if (max == min) {
            return min;
        } else if (max < min) {
            return getRandomNumberInRange(max, min);
        } else {
            return sRandom.nextInt() % max + min;
        }
    }

    /**
     * Gets a random number between 0 (inclusive) and a maximum (exclusive)
     * @param max 1 + The maximum number that the random number can be.
     * @return A random number between 0 and the maximum number.
     */
    public static int getRandomNumberInRange(int max) {
        return getRandomNumberInRange(0, max);
    }

    /**
     * Gets a random element from a list.
     * @param list The list to get a random element from.
     * @return A random element from the list, or null if the list is empty or null.
     */
    public static <T> T getRandomElementInList(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return list.get(getRandomNumberInRange(list.size()));
        }
    }

    /**
     * Removes a random element from a list.
     * @param list The list to remove an element from.
     * @return The element removed from the list, or null if the list is empty or null.
     */
    public static <T> T removeRandomElementInList(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return list.remove(getRandomNumberInRange(list.size()));
        }
    }
}
