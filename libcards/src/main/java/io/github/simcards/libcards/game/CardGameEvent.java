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

    public static CardGameEvent fromJSON(JSONObject input) throws JSONException {
        if (!input.getString("type").equalsIgnoreCase("input")) {
            throw new JSONException("input msg is not of type input");
        }
        Player p = new Player(input.getInt("player_id"));
        JSONArray jDecks = input.getJSONArray("decks");
        JSONArray jCards = input.getJSONArray("cards");

        List<Deck> decks = new ArrayList<>();
        for(int i = 0; i < jDecks.length(); i++) {
            int deckId = jDecks.getInt(i);
            Deck d = Environment.getEnvironment().getDeck(deckId);
            decks.add(d);
        }

        List<List<Card>> cards = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            JSONArray jNestedCards = jCards.getJSONArray(i);
            List<Card> nestedCards = new ArrayList<>();
            for (int j = 0; j < cards.size(); j++) {
                JSONObject jCard = jNestedCards.getJSONObject(j);
                Card c = new Card(Rank.values()[jCard.getInt("rank")], Suit.values()[jCard.getInt("suit")], jCard.getInt("id"));
                nestedCards.add(c);
            }
            cards.add(nestedCards);
        }

        return new CardGameEvent(p, decks, cards);
    }

    public JSONObject toJSON() {
        JSONObject msg = new JSONObject();
        msg.put("type", "input");
        msg.put("player_id", this.player.ID);

        // put decks
        JSONArray deckIds = new JSONArray(decks.size());
        for (int i = 0; i < decks.size(); i++) {
            int deckId = decks.get(i).id;
            deckIds.put(deckId);
        }
        msg.put("decks", deckIds);

        // put cards
        JSONArray jCards = new JSONArray();
        for (int i = 0; i < cards.size(); i++) {
            JSONArray jNestedCards = new JSONArray();
            for (int j = 0; j < cards.get(i).size(); j++) {
                jNestedCards.put(cardToJSON(cards.get(i).get(j)));
            }
            jCards.put(jCards);
        }
        msg.put("cards", jCards);

        return msg;
    }

    private static JSONObject cardToJSON(Card c) {
        JSONObject jCard = new JSONObject();
        jCard.put("id", c.id);
        jCard.put("rank", c.rank.ordinal());
        jCard.put("suit", c.suit.ordinal());
        return jCard;
    }

//    private static JSONArray deckToJSON(Deck d) {
//        JSONArray cards = new JSONArray();
//        cards.put(d.id);
//        for (int i = 0; i < d.size(); i++) {
//            Card c = d.getOffsetTopCard(i);
//            cards.put(c.id);
//            cards.put(c.rank);
//            cards.put(c.suit);
//        }
//    }
//
//    private static Deck jsonArrayToDeck(JSONArray cards) {
//        Deck d = new Deck()
//    }
}
