package io.github.simcards.simcards.util;

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
     * Initializes a vector with initial coordinates.
     * @param x The x coordinate of the vector.
     * @param y The y coordinate of the vector.
     */
    public Position(float x, float y) {
        this.x = x;
        this.y = y;
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

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
