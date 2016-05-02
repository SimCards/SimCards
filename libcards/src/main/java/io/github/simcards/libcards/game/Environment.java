package io.github.simcards.libcards.game;

import java.util.HashMap;
import java.util.Map;

import io.github.simcards.libcards.graphics.DeckView;
import io.github.simcards.libcards.graphics.GameScreen;
import io.github.simcards.libcards.network.DeckUpdater;
import io.github.simcards.libcards.graphics.HandView;
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
        addNewDeck(deck, gridPosition, 0);
    }

    /**
     * Adds a deck to the environment.
     * @param deck The deck to add to the environment.
     * @param gridPosition The position of the deck.
     * @param rotation The rotation angle (in degrees) of the deck.
     */
    public void addNewDeck(Deck deck, GridPosition gridPosition, float rotation) {
        decks.put(deck.id, deck);
        DeckUpdater.addDeck(deck, gridPosition, rotation);
    }

    /**
     * Adds a hand to the environment.
     * @param deck The hand to add to the environment.
     */
    public void addHand(Deck deck) {
        decks.put(deck.id, deck);
        DeckUpdater.addDeck(deck, deck.playerID);
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
        Deck d = decks.remove(id);
        DeckUpdater.removeDeck(id);
        return d;
    }

    /**
     * Handles a deck being touched.
     * @param deckID The ID of the deck being touched.
     * @param cardID The ID of the card being touched.
     */
    public void touchDeck(int playerID, int deckID, int cardID) {
        Deck deck = getDeck(deckID);
        Card selectedCard = null;
        for (Card card : deck.cards) {
            if (card.id == cardID) {
                selectedCard = card;
            }
        }

        // Test code.
//        System.out.println(selectedCard.toString());
//        deck.remove(selectedCard);

        if (touchHandler != null && selectedCard != null) {
            touchHandler.handleTouch(playerID, deck, selectedCard);
        }
    }

    /**
     * Sets the touch handler for the environment.
     * @param handler The touch handler for the environment.
     */
    public void registerTouchHandler(TouchHandler handler) {
        touchHandler = handler;
    }
}
