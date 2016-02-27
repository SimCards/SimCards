package io.github.simcards.libcards.game;

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
}