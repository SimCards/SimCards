package io.github.simcards.simcards.game;

import java.util.ArrayList;
import java.util.List;

import io.github.simcards.simcards.util.GridPosition;

/**
 * A field on which card games are played.
 */
public class Environment {

    /** The decks present in the environment. */
    private List<Deck> decks = new ArrayList<>();

    /**
     * Adds a deck to the environment.
     * @param deck The deck to add to the environment.
     */
    public void addNewDeck(Deck deck) {
        decks.add(deck);
    }
}
