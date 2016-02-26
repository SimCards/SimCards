package io.github.simcards.libcards.util;

/**
 * A rendering bounding box around an object.
 */
public class BoundingBox {

    /** The x coordinate of the left side of the bounding box. */
    private float mLeft;
    /** The x coordinate of the right side of the bounding box. */
    private float mRight;
    /** The y coordinate of the bottom of the bounding box. */
    private float mBottom;
    /** The y coordinate of the top of the bounding box. */
    private float mTop;

    /**
     * Creates a bounding box.
     * @param left The x coordinate of the left side of the bounding box.
     * @param right The x coordinate of the right side of the bounding box.
     * @param bottom The y coordinate of the bottom of the bounding box.
     * @param top The y coordinate of the top of the bounding box.
     */
    public BoundingBox(float left, float right, float bottom, float top) {
        mLeft = left;
        mRight = right;
        mBottom = bottom;
        mTop = top;
    }

    /**
     * Checks if a position is inside the bounding box.
     * @param position The position to check for being inside the bounding box.
     * @return Whether the position is inside the bounding box.
     */
    public boolean isInside(Position position) {
        return MathUtil.isBetween(position.x, mLeft, mRight) &&
                MathUtil.isBetween(position.y, mBottom, mTop);
    }
}
