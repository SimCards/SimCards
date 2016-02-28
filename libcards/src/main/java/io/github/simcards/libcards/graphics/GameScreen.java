package io.github.simcards.libcards.graphics;

import java.util.HashMap;
import java.util.Map;

import io.github.simcards.libcards.game.Environment;
import io.github.simcards.libcards.util.Position;

/**
 * Handles deck display and deck touching.
 */
public class GameScreen {

    /** The decks on the field. */
    private Map<Integer, DeckView> decks = new HashMap<>();

    /** The ID of the client player in the game. */
    public int playerID;

    /** The singleton instance of the screen. */
    private static GameScreen gameScreen = new GameScreen();

    /**
     * Gets the singleton instance of the screen.
     * @return The singleton instance of the screen.
     */
    public static GameScreen getScreen() {
        return gameScreen;
    }

    /**
     * Creates a screen.
     */
    private GameScreen() {
    }

    /**
     * Adds a deck to the screen.
     * @param deck The deck to add to the screen.
     */
    public void addNewDeck(DeckView deck) {
        decks.put(deck.id, deck);
    }

    /**
     * Gets a deck by its ID.
     * @param id The ID of the deck.
     * @return The deck with the specified ID.
     */
    public DeckView getDeck(int id) {
        return decks.get(id);
    }

    /**
     * Removes a deck from the environment.
     * @param id The ID of the deck to remove.
     * @return The deck removed from the environment.
     */
    public DeckView removeDeck(int id) {
        return decks.remove(id);
    }

    /**
     * Acts upon the decks on the screen when a touch event occurs.
     * @param position The position where the touch event occurred.
     */
    public void touch(Position position) {
        for (DeckView deck : decks.values()) {
            int cardTouched = deck.getTouched(position);
            if (cardTouched != -1) {
                // TODO: Change to a packet.
                Environment.getEnvironment().touchDeck(deck.id, cardTouched);
                return;
            }
        }
    }
}
