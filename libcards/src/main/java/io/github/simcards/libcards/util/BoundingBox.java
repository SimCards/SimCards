package io.github.simcards.libcards.util;

/**
 * A rendering bounding box around an object.
 */
public class BoundingBox {

    /** The x coordinate of the left side of the bounding box. */
    private float left;
    /** The x coordinate of the right side of the bounding box. */
    private float right;
    /** The y coordinate of the bottom of the bounding box. */
    private float bottom;
    /** The y coordinate of the top of the bounding box. */
    private float top;

    /**
     * Creates a bounding box.
     * @param left The x coordinate of the left side of the bounding box.
     * @param right The x coordinate of the right side of the bounding box.
     * @param bottom The y coordinate of the bottom of the bounding box.
     * @param top The y coordinate of the top of the bounding box.
     */
    public BoundingBox(float left, float right, float bottom, float top) {
        this.left = left;
        this.right = right;
        this.bottom = bottom;
        this.top = top;
    }

    /**
     * Checks if a position is inside the bounding box.
     * @param position The position to check for being inside the bounding box.
     * @param rotation Whether to rotate the bounding box before checking containment.
     * @return Whether the position is inside the bounding box.
     */
    public boolean isInside(Position position, float rotation) {
        if (rotation == 0) {
            return MathUtil.isBetween(position.x, left, right) &&
                    MathUtil.isBetween(position.y, bottom, top);
        } else {
            Position center = new Position(
                    MathUtil.getAverage(left, right), MathUtil.getAverage(bottom, top));
            Position leftOffset = new Position(center.x - left, 0);
            Position bottomOffset = new Position(0, center.y - bottom);
            leftOffset.rotate(-rotation);
            bottomOffset.rotate(-rotation);
            Position leftBottom = leftOffset.clone();
            leftBottom.addPosition(bottomOffset);
            Position leftTop = leftOffset.clone();
            leftTop.addPosition(bottomOffset.getInverse());
            Position[] corners = new Position[]{
                center.clone(), center.clone(), center.clone(), center.clone()};
            corners[0].addPosition(leftBottom);
            corners[1].addPosition(leftTop);
            corners[2].addPosition(leftBottom.getInverse());
            corners[3].addPosition(leftTop.getInverse());

            int intersections = 0;
            for (int i = 0; i < 4; i++) {
                if (MathUtil.linesIntersect(corners[i].x, corners[i].y, corners[(i + 1) % 4].x, corners[(i + 1) % 4].y,
                position.x, position.y, 1000000, 1000000)) {
                    intersections++;
                }
            }
            return intersections % 2 == 1;
        }
    }
}
