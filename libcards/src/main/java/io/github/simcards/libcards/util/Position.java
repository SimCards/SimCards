package io.github.simcards.libcards.util;

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
     * Initializes a position from a 4D vector.
     * @param vector The vector to initialize the position with.
     */
    public Position(float[] vector) {
        this(vector[0], vector[1]);
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
     * Gets a position that is the inverse of this position.
     * @return The position that is the inverse of this position.
     */
    public Position getInverse() {
        return new Position(-x, -y);
    }

    /**
     * Returns a 4D vector representing the position.
     * @return A 4D vector representing the position.
     */
    public float[] convertToVector() {
        return new float[]{x, y, 0, 1};
    }

    /**
     * Clones the position.
     * @return A clone of the position.
     */
    public Position clone() {
        return new Position(x, y);
    }

    /**
     * Rotates the position around an angle (in degrees).
     * @param rotation The angle to rotate the position by.
     */
    public void rotate(float rotation) {
        float[] rotationMatrix = new float[16];
        Matrix.setRotateM(rotationMatrix, 0, rotation, 0, 0, 1);
        float[] cornerVector = convertToVector();
        float[] rotateResultVector = new float[4];
        Matrix.multiplyMV(rotateResultVector, 0, rotationMatrix, 0, cornerVector, 0);
        x = rotateResultVector[0];
        y = rotateResultVector[1];
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
