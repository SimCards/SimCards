package io.github.simcards.libcards.game;

import io.github.simcards.libcards.game.enums.Arrangement;
import io.github.simcards.libcards.game.enums.Facing;

/**
 * The visibility settings of a card deck.
 */
public class Visibility {

    /** Which direction cards face. */
    public final Facing facing;
    /** Whether the number of cards in the deck is displayed. */
    public final boolean hasCounter;
    /** The arrangement of the cards in the deck. */
    public final Arrangement arrangement;

    /**
     * Initializes visibility settings.
     * @param facing Which direction cards face.
     */
    public Visibility(Facing facing) {
        this(facing, false, Arrangement.STACKED);
    }

    /**
     * Initializes visibility settings.
     * @param facing Which direction cards face.
     * @param hasCounter Whether the number of cards in the deck is displayed.
     * @param arrangement The arrangement of the cards in the deck.
     */
    public Visibility(Facing facing, boolean hasCounter, Arrangement arrangement) {
        this.facing = facing;
        this.hasCounter = hasCounter;
        this.arrangement = arrangement;
    }
}
