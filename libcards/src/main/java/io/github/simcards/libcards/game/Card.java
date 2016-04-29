package io.github.simcards.libcards.game;

import io.github.simcards.libcards.game.enums.Rank;
import io.github.simcards.libcards.game.enums.Suit;

/**
 * A card to render on the screen.
 */
public class Card {

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
        this.rank = rank;
        this.suit = suit;
        this.id = idCounter++;
    }

    @Override
    public String toString() {
        return this.rank.toString() + " of " + this.suit.toString();
    }
}