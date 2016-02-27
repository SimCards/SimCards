package io.github.simcards.libcards.graphics;

import io.github.simcards.libcards.game.Card;
import io.github.simcards.libcards.util.Factory;
import io.github.simcards.libcards.util.Middleman;
import io.github.simcards.libcards.util.Position;

/**
 * A shape used for rendering cards.
 */
public class CardShape {

    /** The ID of the shape, used to sync it with server-side cards. */
    final int id;
    /** The card that this shape is representing. */
    public Card card;
    /** The shape used to render the card. */
    private Shape shape;

    /** Whether the card is facing up, showing its type. */
    private boolean faceUp = true;

    /** The width of cards. */
    static final float CARD_WIDTH = 0.225f;
    /** The height of cards. */
    public static final float CARD_HEIGHT = 0.363f;

    /**
     * Adds a card shape to the renderer.
     * @param card The card that the shape is representing.
     * @param position The position of the card on the field.
     */
    public CardShape(Card card, Position position) {
        id = card.id;
        this.card = card;
        float halfCardWidth = CARD_WIDTH / 2;
        float halfCardHeight = CARD_HEIGHT / 2;
        float top = position.y + halfCardHeight;
        float bottom = position.y - halfCardHeight;
        float right = -position.x + halfCardWidth;
        float left = -position.x - halfCardWidth;
        float[] textureCoordinates;
        if (!Factory.gl().isDesktopGL()) {
            textureCoordinates =
                    new float[]{
                            1.0f, 0.0f,
                            1.0f, 1.0f,
                            0.0f, 1.0f,
                            0.0f, 0.0f,
                    };
        } else {
            textureCoordinates =
                    new float[]{
                            1.0f, 1.0f,
                            1.0f, 0.0f,
                            0.0f, 0.0f,
                            0.0f, 1.0f,
                    };
        }
        shape = new Shape(new float[]{
                left, top, 0.0f,
                left, bottom, 0.0f,
                right, bottom, 0.0f,
                right, top, 0.0f},
                new short[]{0, 1, 2, 0, 2, 3},
                textureCoordinates,
                Middleman.getImageLocation(this));
        GLRenderer.addShape(shape);
    }

    /**
     * Removes the card from the render list.
     */
    public void removeShape() {
        GLRenderer.removeShape(shape);
    }

    /**
     * Returns whether the card is facing up.
     * @return whether the card is facing up.
     */
    public boolean getFaceUp() {
        return this.faceUp;
    }

    /**
     * Sets whether the card is facing up.
     * @param newFaceUp Whether the card is facing up.
     */
    public void setFaceUp(boolean newFaceUp) {
        faceUp = newFaceUp;
        if (shape != null) {
            shape.textureID = Middleman.getImageLocation(this);
            shape.resetTexture();
        }
    }

    /**
     * Gets the screen distance from a card's center to its side.
     * @return The screen distance from a card's center to its side.
     */
    public static float getCenterOffsetX() {
        return CARD_WIDTH / 2;
    }

    /**
     * Gets the screen distance from a card's center to its top or bottom.
     * @return The screen distance from a card's center to its top or bottom.
     */
    public static float getCenterOffsetY() {
        return CARD_HEIGHT / 2;
    }
}
