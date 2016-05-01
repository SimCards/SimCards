package io.github.simcards.libcards.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vishal on 4/30/16.
 */
public class EventMessage implements Serializable {
    private int playerId;
    private List<Integer> deckIds;
    private List<List<Integer>> cardIds;

    public EventMessage(int playerId) {
        this(playerId, new ArrayList<Integer>(), new ArrayList<List<Integer>>());
    }

    public EventMessage(int playerId, int deckId, int cardId) {
        this(playerId);
        ArrayList<Integer> cardIdsForDeck = new ArrayList<>(1);
        cardIdsForDeck.add(cardId);
        this.addDeck(deckId, cardIdsForDeck);
    }

    public EventMessage(int playerId, List<Integer> deckIds, List<List<Integer>> cardIds) {
        this.playerId = playerId;
        this.deckIds = deckIds;
        this.cardIds = cardIds;
    }

    /**
     * Add a deck and associated cards to event
     * @param deckId the deckId to add
     * @param cardIdsForDeck the cardIds associated with the deck
     */
    public void addDeck(int deckId, List<Integer> cardIdsForDeck) {
        deckIds.add(deckId);
        cardIds.add(cardIdsForDeck);
    }

    public int getPlayerId() {
        return this.playerId;
    }

    public List<Integer> getDeckIds() {
        return this.deckIds;
    }

    public List<List<Integer>> getCardIds() {
        return this.cardIds;
    }
}
