package io.github.simcards.simcards.game;

import io.github.simcards.simcards.R;
import io.github.simcards.simcards.client.graphics.GLRenderer;
import io.github.simcards.simcards.client.graphics.Shape;
import io.github.simcards.simcards.util.Position;

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
    private static final float CARD_WIDTH = 0.225f;
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
        shape = new Shape(new float[]{
                left, top, 0.0f,
                left, bottom, 0.0f,
                right, bottom, 0.0f,
                right, top, 0.0f},
                new short[]{0, 1, 2, 0, 2, 3},
                new float[]{
                        1.0f, 0.0f,
                        1.0f, 1.0f,
                        0.0f, 1.0f,
                        0.0f, 0.0f,
                },
                getImageLocation());
        GLRenderer.addShape(shape);
    }

    /**
     * Removes the card from the render list.
     */
    public void removeShape() {
        GLRenderer.removeShape(shape);
    }

    /**
     * Sets whether the card is facing up.
     * @param newFaceUp Whether the card is facing up.
     */
    public void setFaceUp(boolean newFaceUp) {
        faceUp = newFaceUp;
        if (shape != null) {
            shape.textureID = getImageLocation();
            shape.resetTexture();
        }
    }

    /**
     * Gets the index of the image representing the card's type.
     * @return The index of the image representing the card's type.
     */
    private int getImageLocation() {
        if (!faceUp) {
            return R.drawable.face_down;
        }
        switch(suit) {
            case BLACKJOKER:
                return R.drawable.black_joker;
            case REDJOKER:
                return R.drawable.red_joker;
            case SPADE:
                switch(rank) {
                    case ACE: return R.drawable.ace_of_spades;
                    case TWO: return R.drawable.two_of_spades;
                    case THREE: return R.drawable.three_of_spades;
                    case FOUR: return R.drawable.four_of_spades;
                    case FIVE: return R.drawable.five_of_spades;
                    case SIX: return R.drawable.six_of_spades;
                    case SEVEN: return R.drawable.seven_of_spades;
                    case EIGHT: return R.drawable.eight_of_spades;
                    case NINE: return R.drawable.nine_of_spades;
                    case TEN: return R.drawable.ten_of_spades;
                    case JACK: return R.drawable.jack_of_spades;
                    case QUEEN: return R.drawable.queen_of_spades;
                    case KING: return R.drawable.king_of_spades;
                }
            case HEART:
                switch(rank) {
                    case ACE: return R.drawable.ace_of_hearts;
                    case TWO: return R.drawable.two_of_hearts;
                    case THREE: return R.drawable.three_of_hearts;
                    case FOUR: return R.drawable.four_of_hearts;
                    case FIVE: return R.drawable.five_of_hearts;
                    case SIX: return R.drawable.six_of_hearts;
                    case SEVEN: return R.drawable.seven_of_hearts;
                    case EIGHT: return R.drawable.eight_of_hearts;
                    case NINE: return R.drawable.nine_of_hearts;
                    case TEN: return R.drawable.ten_of_hearts;
                    case JACK: return R.drawable.jack_of_hearts;
                    case QUEEN: return R.drawable.queen_of_hearts;
                    case KING: return R.drawable.king_of_hearts;
                }
            case CLUB:
                switch(rank) {
                    case ACE: return R.drawable.ace_of_clubs;
                    case TWO: return R.drawable.two_of_clubs;
                    case THREE: return R.drawable.three_of_clubs;
                    case FOUR: return R.drawable.four_of_clubs;
                    case FIVE: return R.drawable.five_of_clubs;
                    case SIX: return R.drawable.six_of_clubs;
                    case SEVEN: return R.drawable.seven_of_clubs;
                    case EIGHT: return R.drawable.eight_of_clubs;
                    case NINE: return R.drawable.nine_of_clubs;
                    case TEN: return R.drawable.ten_of_clubs;
                    case JACK: return R.drawable.jack_of_clubs;
                    case QUEEN: return R.drawable.queen_of_clubs;
                    case KING: return R.drawable.king_of_clubs;
                }
            case DIAMOND:
                switch(rank) {
                    case ACE: return R.drawable.ace_of_diamonds;
                    case TWO: return R.drawable.two_of_diamonds;
                    case THREE: return R.drawable.three_of_diamonds;
                    case FOUR: return R.drawable.four_of_diamonds;
                    case FIVE: return R.drawable.five_of_diamonds;
                    case SIX: return R.drawable.six_of_diamonds;
                    case SEVEN: return R.drawable.seven_of_diamonds;
                    case EIGHT: return R.drawable.eight_of_diamonds;
                    case NINE: return R.drawable.nine_of_diamonds;
                    case TEN: return R.drawable.ten_of_diamonds;
                    case JACK: return R.drawable.jack_of_diamonds;
                    case QUEEN: return R.drawable.queen_of_diamonds;
                    case KING: return R.drawable.king_of_diamonds;
                }
        }
        return -1;
    }
}