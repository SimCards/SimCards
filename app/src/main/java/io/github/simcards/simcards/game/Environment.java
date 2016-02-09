package io.github.simcards.simcards.game;

import java.util.ArrayList;
import java.util.List;

/**
 * A field on which card games are played.
 */
public class Environment {

    /** The decks present in the environment. */
    public List<Deck> decks = new ArrayList<>();

    /** The current instance of an environment. */
    private static Environment sEnvironment;

    /**
     * Gets the current environment instance.
     * @return The current environment instance.
     */
    public static Environment getEnvironment() {
        if (sEnvironment == null) {
            sEnvironment = new Environment();
        }
        return sEnvironment;
    }

    /**
     * Creates an environment.
     */
    private Environment() {}

    /**
     * Adds a deck to the environment.
     * @param deck The deck to add to the environment.
     */
    public void addNewDeck(Deck deck) {
        decks.add(deck);
    }
}
