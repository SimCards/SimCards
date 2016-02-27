package io.github.simcards.libcards.util;

import io.github.simcards.libcards.graphics.CardShape;

/**
 * Keeps track of position in grid cells.
 */
public class GridPosition {

    /** The x coordinate of the grid position. */
    public int x;
    /** The y coordinate of the grid position. */
    public int y;

    /**
     * Initializes a grid position at (0, 0).
     */
    public GridPosition() {
    }

    /**
     * Initializes a grid position.
     * @param x The x coordinate of the grid position.
     * @param y The y coordinate of the grid position.
     */
    public GridPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the center of the grid cell in world coordinates.
     * @return The center of the grid cell in world coordinates.
     */
    public Position getWorldPosition() {
        return new Position(x * CardShape.CARD_HEIGHT, y * CardShape.CARD_HEIGHT);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof GridPosition) {
            GridPosition otherPosition = (GridPosition)other;
            return otherPosition.x == x && otherPosition.y == y;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return x << 16 | y & 0xFFFF;
    }
}
