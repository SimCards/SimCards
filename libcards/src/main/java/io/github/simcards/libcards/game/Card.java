package io.github.simcards.libcards.game;

import java.io.Serializable;

import io.github.simcards.libcards.game.enums.Rank;
import io.github.simcards.libcards.game.enums.Suit;

/**
 * A card to render on the screen.
 */
public class Card implements Serializable {

    /** The ID of the card, used to sync with client-side CardShapes. */
    public final int id;
    /** The card's rank. */
    public final Rank rank;
    /** The card's suit. */
    public final Suit suit;

    /** Counter to assign unique IDs to cards. */
    private static int idCounter;

    /**
     * Initializes a card.
     * @param rank The card's rank.
     * @param suit The card's suit.
     */
    public Card(Rank rank, Suit suit) {
        this(rank, suit, idCounter++);
    }

    /**
     * Initializes a card
     * @param rank The cards' rank
     * @param suit The card's suit
     * @param id The card's unique identifier
     */
    protected Card(Rank rank, Suit suit, int id) {
        this.rank = rank;
        this.suit = suit;
        this.id = id;
    }
}