package io.github.simcards.simcards.util;

import io.github.simcards.simcards.game.Card;

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
        return new Position(x * Card.CARD_HEIGHT, y * Card.CARD_HEIGHT);
    }
}
