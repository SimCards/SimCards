package io.github.simcards.libcards.game;

import io.github.simcards.libcards.util.Middleman;
import io.github.simcards.libcards.graphics.OtherGLRenderer;
import io.github.simcards.libcards.util.Factory;
import io.github.simcards.libcards.graphics.Shape;
import io.github.simcards.libcards.util.Position;

/**
 * A card to render on the screen.
 */
public class Card {

    /** The card's rank. */
    public final Rank rank;
    /** The card's suit. */
    public final Suit suit;
    /** Whether the card is facing up, showing its type. */
    private boolean faceUp = true;

    /** The width of cards. */
    static final float CARD_WIDTH = 0.225f;
    /** The height of cards. */
    public static final float CARD_HEIGHT = 0.363f;

    /** The shape used to render the card. */
    private Shape shape;

    /**
     * Initializes a card.
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Creates a shape used to render to card.
     * @param position The position of the center of the card.
     */
    public void createShape(Position position) {
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
        OtherGLRenderer.addShape(shape);
    }

    /**
     * Removes the card from the render list.
     */
    public void removeShape() {
        OtherGLRenderer.removeShape(shape);
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
     * returns whether the card is facing up
     * @return whether the card is facing up
     */
    public boolean getFaceUp() {
        return this.faceUp;
    }

    /**
     * Gets the screen distance from a card's center to its side.
     * @return The screen distance from a card's center to its side.
     */
    public static float getScaledCenterOffsetX() {
        return Card.CARD_WIDTH / 2 * OtherGLRenderer.sCamera.scale;
    }

    /**
     * Gets the screen distance from a card's center to its top or bottom.
     * @return The screen distance from a card's center to its top or bottom.
     */
    public static float getScaledCenterOffsetY() {
        return Card.CARD_HEIGHT / 2 * OtherGLRenderer.sCamera.scale;
    }
}