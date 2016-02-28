package io.github.simcards.libcards.util;

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

    /**
     * Gets the average of a list of values.
     * @param values The values to get an average for.
     * @return The average of the specified list of values.
     */
    public static float getAverage(float... values) {
        if (values.length == 0) {
            return 0;
        } else {
            float sum = 0;
            for (float value : values) {
                sum += value;
            }
            return sum / (float)values.length;
        }
    }

    /**
     * Tests if the line segment from {@code (x1,y1)} to
     * {@code (x2,y2)} intersects the line segment from {@code (x3,y3)}
     * to {@code (x4,y4)}.
     *
     * @param x1 the X coordinate of the start point of the first
     *           specified line segment
     * @param y1 the Y coordinate of the start point of the first
     *           specified line segment
     * @param x2 the X coordinate of the end point of the first
     *           specified line segment
     * @param y2 the Y coordinate of the end point of the first
     *           specified line segment
     * @param x3 the X coordinate of the start point of the second
     *           specified line segment
     * @param y3 the Y coordinate of the start point of the second
     *           specified line segment
     * @param x4 the X coordinate of the end point of the second
     *           specified line segment
     * @param y4 the Y coordinate of the end point of the second
     *           specified line segment
     * @return <code>true</code> if the first specified line segment
     *                  and the second specified line segment intersect
     *                  each other; <code>false</code> otherwise.
     * @since 1.2
     */
    public static boolean linesIntersect(double x1, double y1,
                                         double x2, double y2,
                                         double x3, double y3,
                                         double x4, double y4) {
        return ((relativeCCW(x1, y1, x2, y2, x3, y3) *
                relativeCCW(x1, y1, x2, y2, x4, y4) <= 0)
                && (relativeCCW(x3, y3, x4, y4, x1, y1) *
                relativeCCW(x3, y3, x4, y4, x2, y2) <= 0));
    }

    /**
     * Returns an indicator of where the specified point
     * {@code (px,py)} lies with respect to the line segment from
     * {@code (x1,y1)} to {@code (x2,y2)}.
     * The return value can be either 1, -1, or 0 and indicates
     * in which direction the specified line must pivot around its
     * first end point, {@code (x1,y1)}, in order to point at the
     * specified point {@code (px,py)}.
     * <p>A return value of 1 indicates that the line segment must
     * turn in the direction that takes the positive X axis towards
     * the negative Y axis.  In the default coordinate system used by
     * Java 2D, this direction is counterclockwise.
     * <p>A return value of -1 indicates that the line segment must
     * turn in the direction that takes the positive X axis towards
     * the positive Y axis.  In the default coordinate system, this
     * direction is clockwise.
     * <p>A return value of 0 indicates that the point lies
     * exactly on the line segment.  Note that an indicator value
     * of 0 is rare and not useful for determining collinearity
     * because of floating point rounding issues.
     * <p>If the point is collinear with the line segment, but
     * not between the end points, then the value will be -1 if the point
     * lies "beyond {@code (x1,y1)}" or 1 if the point lies
     * "beyond {@code (x2,y2)}".
     *
     * @param x1 the X coordinate of the start point of the
     *           specified line segment
     * @param y1 the Y coordinate of the start point of the
     *           specified line segment
     * @param x2 the X coordinate of the end point of the
     *           specified line segment
     * @param y2 the Y coordinate of the end point of the
     *           specified line segment
     * @param px the X coordinate of the specified point to be
     *           compared with the specified line segment
     * @param py the Y coordinate of the specified point to be
     *           compared with the specified line segment
     * @return an integer that indicates the position of the third specified
     *                  coordinates with respect to the line segment formed
     *                  by the first two specified coordinates.
     * @since 1.2
     */
    public static int relativeCCW(double x1, double y1,
                                  double x2, double y2,
                                  double px, double py) {
        x2 -= x1;
        y2 -= y1;
        px -= x1;
        py -= y1;
        double ccw = px * y2 - py * x2;
        if (ccw == 0.0) {
            // The point is collinear, classify based on which side of
            // the segment the point falls on.  We can calculate a
            // relative value using the projection of px,py onto the
            // segment - a negative value indicates the point projects
            // outside of the segment in the direction of the particular
            // endpoint used as the origin for the projection.
            ccw = px * x2 + py * y2;
            if (ccw > 0.0) {
                // Reverse the projection to be relative to the original x2,y2
                // x2 and y2 are simply negated.
                // px and py need to have (x2 - x1) or (y2 - y1) subtracted
                //    from them (based on the original values)
                // Since we really want to get a positive answer when the
                //    point is "beyond (x2,y2)", then we want to calculate
                //    the inverse anyway - thus we leave x2 & y2 negated.
                px -= x2;
                py -= y2;
                ccw = px * x2 + py * y2;
                if (ccw < 0.0) {
                    ccw = 0.0;
                }
            }
        }
        return (ccw < 0.0) ? -1 : ((ccw > 0.0) ? 1 : 0);
    }
}
