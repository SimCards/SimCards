package io.github.simcards.libcards.game.enums;

/**
 * Represents a card suit.
 */
public enum Suit {
    CLUB,
    DIAMOND,
    HEART,
    SPADE,
    BLACKJOKER,
    REDJOKER;

    /**
     * Checks if the suit is a joker.
     * @return Whether the suit is a joker.
     */
    public boolean isJoker() {
        return this == BLACKJOKER || this == REDJOKER;
    }

    /**
     * Checks if the suit is a black color.
     * @return Whether the suit is a black color.
     */
    public boolean isBlack() {
        return this == SPADE || this == CLUB;
    }

    /**
     * Checks if the suit is a red color.
     * @return Whether the suit is a red color.
     */
    public boolean isRed() {
        return this == HEART || this == DIAMOND;
    }
}
