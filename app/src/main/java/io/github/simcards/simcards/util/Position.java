package io.github.simcards.simcards.util;

import android.view.MotionEvent;

/**
 * A position to use for rendering.
 */
public class Position {
    /** The coordinates of the position. */
    public volatile float x, y;

    /**
     * Initializes a position at (0,0).
     */
    public Position() {
    }

    /**
     * Initializes a position with initial coordinates.
     * @param x The x coordinate of the position.
     * @param y The y coordinate of the position.
     */
    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Initializes a position from a touch event.
     * @param event The touch event to initialize the position with.
     */
    public Position(MotionEvent event) {
        this(event.getX(), event.getY());
    }

    /**
     * Initializes a position from a 4x4 matrix.
     * @param matrix The matrix to initialize the position with.
     */
    public Position(float[] matrix) {
        this(matrix[0], matrix[5]);
    }

    /**
     * Adds a value to the x coordinate.
     * @param offsetX The value to add to the x coordinate.
     */
    public void addX(float offsetX) {
        x += offsetX;
    }

    /**
     * Adds a value to the y coordinate.
     * @param offsetY The value to add to the y coordinate.
     */
    public void addY(float offsetY) {
        y += offsetY;
    }

    /**
     * Adds a position offset to the current position.
     * @param offset The position offset to add.
     */
    public void addPosition(Position offset) {
        addX(offset.x);
        addY(offset.y);
    }

    /**
     * Adds a position offset to the current position.
     * @param offsetX The value to add to the x coordinate.
     * @param offsetY The value to add to the y coordinate.
     */
    public void addPosition(float offsetX, float offsetY) {
        addX(offsetX);
        addY(offsetY);
    }

    /**
     * Adds a value to the x coordinate, limited to a certain range.
     * @param offsetX The value to add to the x coordinate.
     * @param minX The minimum value of the x coordinate.
     * @param maxX The maximum value of the x coordinate.
     */
    public void addXLimited(float offsetX, float minX, float maxX) {
        x = MathUtil.addLimited(x, offsetX, minX, maxX);
    }

    /**
     * Adds a value to the y coordinate, limited to a certain range.
     * @param offsetY The value to add to the y coordinate.
     * @param minY The minimum value of the y coordinate.
     * @param maxY The maximum value of the y coordinate.
     */
    public void addYLimited(float offsetY, float minY, float maxY) {
        y = MathUtil.addLimited(y, offsetY, minY, maxY);
    }

    /**
     * Scales the position by a certain amount.
     * @param scaleValue The amount to scale the position by.
     */
    public void scale(float scaleValue) {
        x *= scaleValue;
        y *= scaleValue;
    }

    /**
     * Inverts the y coordinate.
     */
    public void invertY() {
        y = -y;
    }

    /**
     * Returns a 4x4 matrix representing the position.
     * @return A 4x4 matrix representing the position.
     */
    public float[] convertToMatrix() {
        return new float[]{x, 0, 0, 0,
                           0, y, 0, 0,
                           0, 0, 0, 0,
                           0, 0, 0, 0};
    }

    /**
     * Clones the position.
     * @return A clone of the position.
     */
    public Position clone() {
        return new Position(x, y);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Position) {
            Position otherPosition = (Position)other;
            return otherPosition.x == x && otherPosition.y == y;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
