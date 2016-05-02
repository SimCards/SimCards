package io.github.simcards.libcards.network;

import java.io.Serializable;

import io.github.simcards.libcards.game.Deck;
import io.github.simcards.libcards.util.GridPosition;

/**
 * Creates message used to update decks.
 * Created by Vishal on 4/29/16.
 */
public class DeckUpdater {

    /**
     * Adds a deck to the field.
     * @param d The deck to add.
     * @param p The position of the deck.
     * @param rotation The rotation of the deck.
     */
    public static void addDeck(Deck d, GridPosition p, float rotation) {
        addDeckMessage(new DeckAddMsg(d, p, rotation));
    }

    /**
     * Adds a hand to the field.
     * @param d The hand to add.
     * @param playerID The ID of the player who owns the hand.
     */
    public static void addDeck(Deck d, int playerID) {
        addDeckMessage(new DeckAddMsg(d, playerID));
    }

    /**
     * Sends a deck add message to clients.
     * @param msg The message to send.
     */
    private static void addDeckMessage(DeckAddMsg msg) {
        SendQueueThread.getThread().addToQueue(new SerializableMsg(MessageType.DECK_ADD, msg));
    }

    public static void updateDeck(Deck d) {
        SendQueueThread.getThread().addToQueue(new SerializableMsg(MessageType.DECK_UPDATE, d));
    }

    /**
     * Sends a deck remove message to clients.
     * @param id The ID of the deck to remove.
     */
    public static void removeDeck(int id) {
        SendQueueThread.getThread().addToQueue(new SerializableMsg(MessageType.DECK_REMOVE, id));
    }

    /**
     * A message containing deck information.
     */
    public static class DeckAddMsg implements Serializable {
        Deck d;
        GridPosition p;
        float rotation;
        /** The ID of the player who owns the deck. */
        int playerID = -1;

        public DeckAddMsg(Deck d, GridPosition p, float rotation) {
            this.d = d;
            this.p = p;
            this.rotation = rotation;
        }

        /**
         * Creates a hand add message.
         * @param d The hand to add.
         * @param playerID The ID of the player who owns the hand.
         */
        public DeckAddMsg(Deck d, int playerID) {
            this.d = d;
            this.playerID = playerID;
        }
    }
}
