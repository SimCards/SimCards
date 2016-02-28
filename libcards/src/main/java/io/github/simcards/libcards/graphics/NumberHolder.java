package io.github.simcards.libcards.graphics;

import io.github.simcards.libcards.util.Matrix;
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
     * @param rotation The rotation angle (in degrees) of the number.
     */
    public NumberHolder(int number, Position position, float rotation) {
        Position currentPosition = position.clone();
        char[] charArray = Integer.toString(number).toCharArray();
        shapes = new NumberShape[charArray.length];

        Position positionOffset = new Position(NumberShape.NUMBER_WIDTH * 1.2f, 0);

        for (int i = 0; i < charArray.length; i++) {
            char digit = charArray[i];
            shapes[i] = new NumberShape(digit, currentPosition, rotation);
            currentPosition.addPosition(positionOffset);
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
