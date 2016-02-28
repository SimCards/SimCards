package io.github.simcards.libcards.game;

import java.util.HashMap;
import java.util.Map;

import io.github.simcards.libcards.graphics.DeckView;
import io.github.simcards.libcards.graphics.GameScreen;
import io.github.simcards.libcards.util.GridPosition;
import io.github.simcards.libcards.util.TouchHandler;

/**
 * A field on which card games are played.
 */
public class Environment {

    /** The decks present in the environment. */
    private Map<Integer, Deck> decks = new HashMap<>();

    /** Responds to touch events. */
    private static TouchHandler touchHandler;

    /** The current instance of an environment. */
    private static Environment environment = new Environment();

    /**
     * Gets the current environment instance.
     * @return The current environment instance.
     */
    public static Environment getEnvironment() {
        return environment;
    }

    /**
     * Creates an environment.
     */
    private Environment() {}

    /**
     * Adds a deck to the environment.
     * @param deck The deck to add to the environment.
     * @param gridPosition The position of the deck.
     */
    public void addNewDeck(Deck deck, GridPosition gridPosition) {
        decks.put(deck.id, deck);
        // TODO: Change to a packet.
        GameScreen.getScreen().addNewDeck(new DeckView(deck, gridPosition));
    }

    /**
     * Gets a deck by its ID.
     * @param id The ID of the deck.
     * @return The deck with the specified ID.
     */
    public Deck getDeck(int id) {
        return decks.get(id);
    }

    /**
     * Removes a deck from the environment.
     * @param id The ID of the deck to remove.
     * @return The deck removed from the environment.
     */
    public Deck removeDeck(int id) {
        // TODO: Change to a packet.
        GameScreen.getScreen().removeDeck(id);
        return decks.remove(id);
    }

    /**
     * Handles a deck being touched.
     * @param deckID The ID of the deck being touched.
     * @param cardID The ID of the card being touched.
     */
    public void touchDeck(int deckID, int cardID) {
        Deck deck = getDeck(deckID);
        Card selectedCard = null;
        for (Card card : deck.cards) {
            if (card.id == cardID) {
                selectedCard = card;
            }
        }
        if (touchHandler != null && selectedCard != null) {
            touchHandler.handleTouch(deck, selectedCard);
        }
        deck.touch();
    }

    /**
     * Sets the touch handler for the environment.
     * @param handler The touch handler for the environment.
     */
    public void registerTouchHandler(TouchHandler handler) {
        touchHandler = handler;
    }
}
