package io.github.simcards.simcards.client.graphics;

import io.github.simcards.simcards.R;

/**
 * A card to render on the screen.
 */
public class Card extends Shape {

    /** The width of cards. */
    private static final float CARD_WIDTH = 0.45f;
    /** The height of cards. */
    private static final float CARD_HEIGHT = 0.726f;

    /**
     * Initializes a card.
     */
    public Card() {
        super(new float[]{
                    -CARD_WIDTH, CARD_HEIGHT, 0.0f,
                    -CARD_WIDTH, -CARD_HEIGHT, 0.0f,
                    CARD_WIDTH, -CARD_HEIGHT, 0.0f,
                    CARD_WIDTH,  CARD_HEIGHT, 0.0f},
                new short[]{ 0, 1, 2, 0, 2, 3 },
                new float[]{
                    0.0f, 1.0f,
                    0.0f, 0.0f,
                    1.0f, 0.0f,
                    1.0f, 1.0f,
                },
                R.drawable.ten_of_spades);
    }
}