package io.github.simcards.simcards.game;

import android.text.method.Touch;

import java.util.ArrayList;
import java.util.List;

import io.github.simcards.simcards.client.ui.TouchHandler;
import io.github.simcards.simcards.util.Position;

/**
 * A field on which card games are played.
 */
public class Environment {

    /** The decks present in the environment. */
    public List<Deck> decks = new ArrayList<>();

    /** The current instance of an environment. */
    private static Environment sEnvironment;

    private static TouchHandler touchHandler;

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

    public void registerTouchHandler(TouchHandler handler) {
        touchHandler = handler;
    }

    public void touch(Position position) {
        for (Deck deck : decks) {
            if (deck.isTouched(position)) {
                if (touchHandler != null)
                    touchHandler.handleTouch(deck);
                deck.touch();
            }
        }
    }
}
