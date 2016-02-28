package io.github.simcards.libcards.util;

import io.github.simcards.libcards.game.Card;
import io.github.simcards.libcards.game.Deck;

/**
 * Handles touch events server-side.
 * Created by Vishal on 2/12/16.
 */
public interface TouchHandler {
    /**
     * Handles a touch event on a deck.
     * @param deck The deck that was touched.
     * @param card The card that was touched.
     */
    void handleTouch(Deck deck, Card card);
}
