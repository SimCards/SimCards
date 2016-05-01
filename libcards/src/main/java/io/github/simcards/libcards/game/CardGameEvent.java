package io.github.simcards.libcards.game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.github.simcards.libcards.game.enums.Rank;
import io.github.simcards.libcards.game.enums.Suit;
import io.github.simcards.libcards.graphics.GameScreen;
import io.github.simcards.libcards.network.EventMessage;

/**
 * Represents an input event to the card game
 */
public class CardGameEvent implements Serializable {

    private final Player player;
    private final List<Deck> decks;
    private final List<List<Card>> cards;

    public CardGameEvent(Player player, List<Deck> decks, List<List<Card>> cards) {
        this.player = player;
        this.decks = decks;
        this.cards = cards;
    }

    public Player getPlayer() {
        return this.player;
    }

    public List<Deck> getDecks() {
        return this.decks;
    }

    public List<List<Card>> getCards() {
        return this.cards;
    }

}
