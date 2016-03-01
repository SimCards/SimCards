package io.github.simcards.simcards.util;

import android.opengl.Matrix;

import java.util.Arrays;

/**
 * Utility math methods.
 */
public class MathUtil {

    /**
     * Adds an offset to a number, keeping it within a certain range of numbers.
     * @param value The value to add to.
     * @param offset The offset to add to the value.
     * @param min The value's minimum.
     * @param max The value's maximum.
     * @return value + offset if the sum is within the range.
     * The limit that the sum exceeded if the sum is not within the range.
     */
    public static float addLimited(float value, float offset, float min, float max) {
        return limitValue(value + offset, min, max);
    }

    /**
     * Limits a value to a certain range.
     * @param value The value to limit.
     * @param min The value's minimum.
     * @param max The value's maximum.
     * @return The given value if it is within the range.
     * The limit that the given value exceeded if the value is not within the range.
     */
    public static float limitValue(float value, float min, float max) {
        return Math.min(max, Math.max(min, value));
    }

    /**
     * Scales a matrix's x and y coordinates by a value.
     * @param matrix The matrix to scale.
     * @param scale The value to scale the matrix by.
     * @return A new matrix that is the original matrix scaled by the value.
     */
    public static float[] scaleMatrix(float[] matrix, float scale) {
        float[] scaledMatrix = Arrays.copyOf(matrix, matrix.length);
        scaledMatrix[0] *= scale;
        scaledMatrix[5] *= scale;
        return scaledMatrix;
    }

    /**
     * Checks if a value is between range of values.
     * @param value The value to check for being within a range.
     * @param min The minimum value that the given value can be.
     * @param max The maximum value that the given value can be.
     * @return Whether the given value is within the given range.
     */
    public static boolean isBetween(float value, float min, float max) {
        return value >= min && value <= max;
    }
}