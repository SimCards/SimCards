package io.github.simcards.libcards.graphics;

import java.util.HashMap;
import java.util.Map;

import io.github.simcards.libcards.game.Card;
import io.github.simcards.libcards.util.ClientTouchHandler;
import io.github.simcards.libcards.util.Position;
import io.github.simcards.libcards.util.TouchHandler;

/**
 * Handles deck display and deck touching.
 */
public class GameScreen {

    /** The decks on the field. */
    private Map<Integer, DeckView> decks = new HashMap<>();
    /** The hand on the screen. */
    private HandView hand;

    /** The ID of the client player in the game. */
    public int playerID = -1;

    /** The singleton instance of the screen. */
    private static GameScreen gameScreen = new GameScreen();

    /** A handler to be called when a deck is touched **/
    private ClientTouchHandler touchHandler;

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
     * Updates the currently displayed hand.
     * @param hand The hand to display.
     */
    public void addHand(HandView hand) {
        if (hand != null) {
            removeDeck(hand.id);
        }
        this.hand = hand;
        addNewDeck(hand);
    }

    /**
     * Redraws the displayed hand.
     */
    void redrawHand() {
        if (hand != null) {
            hand.redraw();
        }
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
        DeckView removedDeck = decks.remove(id);
        if (removedDeck != null) {
            removedDeck.remove();
        }
        return removedDeck;
    }

    /**
     * Acts upon the decks on the screen when a touch event occurs.
     * @param position The position where the touch event occurred.
     */
    public void touch(Position position) {
        for (DeckView deck : decks.values()) {
            int cardTouched = deck.getTouched(position);
            if (cardTouched != -1) {
                touchHandler.onTouched(deck.id, cardTouched);
                return;
            }
        }
    }

    public void setTouchHandler(ClientTouchHandler touchHandler) {
        this.touchHandler = touchHandler;
    }

    /**
     * Sets the player's ID and rotates the camera accordingly.
     * @param id The ID of the player.
     */
    public void setPlayerID(int id) {
        if (playerID == -1) {
            playerID = id;
        }
        int numPlayers = id + 1;
        GLRenderer.camera.rotation = 360 / numPlayers * playerID;
    }
}
