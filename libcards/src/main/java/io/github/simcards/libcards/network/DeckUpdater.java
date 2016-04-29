package io.github.simcards.libcards.network;

import org.zeromq.ZMQ;

import java.io.Serializable;

import io.github.simcards.libcards.game.Deck;
import io.github.simcards.libcards.util.GridPosition;

/**
 * Created by Vishal on 4/29/16.
 */
public class DeckUpdater {

    public static void addDeck(Deck d, GridPosition p, float rotation) {
        SendQueueThread.getThread().addToQueue(new SerializableMsg(MessageType.DECK_ADD, d));
    }

    public static void updateDeck(Deck d) {
        SendQueueThread.getThread().addToQueue(new SerializableMsg(MessageType.DECK_UPDATE, d));

    }

    public static void removeDeck(int id) {
        SendQueueThread.getThread().addToQueue(new SerializableMsg(MessageType.DECK_UPDATE, id));
    }

    public class DeckAddMsg implements Serializable {
        Deck d;
        GridPosition p;
        float rotation;

        public DeckAddMsg(Deck d, GridPosition p, float rotation) {
            this.d = d;
            this.p = p;
            this.rotation = rotation;
        }
    }
}
