package io.github.simcards.libcards.graphics;

import io.github.simcards.libcards.util.Position;

/**
 * Renders a multi-digit number on the screen.
 */
public class NumberHolder {

    /** The number shapes used to render the number. */
    private NumberShape[] shapes;

    /**
     * Initializes the number holder.
     * @param number The number to render.
     * @param position The position to render the number at.
     */
    public NumberHolder(int number, Position position) {
        Position currentPosition = position.clone();
        char[] charArray = Integer.toString(number).toCharArray();
        shapes = new NumberShape[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            char digit = charArray[i];
            shapes[i] = new NumberShape(digit, currentPosition);
            currentPosition.x += NumberShape.NUMBER_WIDTH * 1.2f;
        }
    }

    /**
     * Renders the number.
     */
    public void render() {
        for (NumberShape number : shapes) {
            GLRenderer.addShape(number.shape);
        }
    }

    /**
     * Removes the number from the screen.
     */
    public void remove() {
        for (NumberShape number : shapes) {
            GLRenderer.removeShape(number.shape);
        }
    }
}
