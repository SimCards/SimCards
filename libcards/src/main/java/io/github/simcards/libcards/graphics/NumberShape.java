package io.github.simcards.libcards.graphics;

import io.github.simcards.libcards.util.Factory;
import io.github.simcards.libcards.util.Middleman;
import io.github.simcards.libcards.util.Position;

/**
 * Used to render numbers.
 */
public class NumberShape {

    /** The shape used to render the number. */
    Shape shape;

    /** The width of numbers. */
    static final float NUMBER_WIDTH = 0.02675f;
    /** The height of numbers. */
    static final float NUMBER_HEIGHT = 0.0375f;

    /**
     * Adds a card shape to the renderer.
     * @param number The number to render.
     * @param position The position of the card on the field.
     */
    public NumberShape(char number, Position position) {
        float halfNumberWidth = NUMBER_WIDTH / 2;
        float halfNumberHeight = NUMBER_HEIGHT / 2;
        float top = position.y + halfNumberHeight;
        float bottom = position.y - halfNumberHeight;
        float right = -position.x + halfNumberWidth;
        float left = -position.x - halfNumberWidth;
        float[] textureCoordinates = getTextureCoordinates(number);
        shape = new Shape(new float[]{
                left, top, 0.0f,
                left, bottom, 0.0f,
                right, bottom, 0.0f,
                right, top, 0.0f},
                new short[]{0, 1, 2, 0, 2, 3},
                textureCoordinates,
                Middleman.getFontLocation());
    }

    /**
     * Gets the texture coordinates of a number.
     * @param numberChar A number character between 0 and 9 to get texture coordinates for.
     * @return Texture coordinates corresponding to the specified number.
     */
    private static float[] getTextureCoordinates(char numberChar) {
        boolean isDesktop = Factory.gl().isDesktopGL();
        int number = numberChar - 48;
        float left, right, top, bottom;
        if (number <= 5) {
            left = 0.25f + 0.125f * number;
            top = isDesktop ? 0.5f : 0.375f;
        } else {
            left = 0.125f * (number - 6);
            top = isDesktop ? 0.375f : 0.5f;
        }
        right = left + 30f / 512f;
        top += 13f / 512f;
        bottom = top + 42f / 512f;
        if (!isDesktop) {
            return new float[]{
                            right, top,
                            right, bottom,
                            left, bottom,
                            left, top,
                    };
        } else {
            return new float[]{
                            right, bottom,
                            right, top,
                            left, top,
                            left, bottom,
                    };
        }
    }
}
