package io.github.simcards.libcards.game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.zeromq.ZMQ;

import java.util.List;
import java.util.Vector;

import io.github.simcards.libcards.game.enums.Facing;
import io.github.simcards.libcards.game.enums.Rank;
import io.github.simcards.libcards.game.enums.Suit;
import io.github.simcards.libcards.network.MessageHandler;
import io.github.simcards.libcards.network.ZeroMQSendThread;
import io.github.simcards.libcards.util.TouchHandler;
import io.github.simcards.libcards.util.GridPosition;
import io.github.simcards.libcards.util.Factory;

/**
 * Created by Vishal on 2/12/16.
 */
public class AbsolutelyRankedWar implements TouchHandler, MessageHandler {

    private Deck[] decks;
    private Deck[] piles;

    private boolean initialized;
    private boolean game_over;

    private int current_player;
    private ZMQ.Socket socket;

    public AbsolutelyRankedWar(ZMQ.Socket socket) {
        this.initialized = false;
        this.socket = socket;
    }

    private void init(int current_player, List<Card> cards) {
        decks = new Deck[2];
        piles = new Deck[2];

        List<Card> deck1 = new Vector<>(26);
        List<Card> deck2 = new Vector<>(26);

        for (int i = 0; i < 26; i++) {
            deck1.add(cards.get(i));
            deck2.add(cards.get(i+26));
        }

        decks[0] = new Deck(deck1, new Visibility(Facing.FACE_DOWN));
        piles[0] = new Deck(new Vector<Card>(), new Visibility(Facing.FACE_UP));

        decks[1] = new Deck(deck2, new Visibility(Facing.FACE_DOWN));
        piles[1] = new Deck(new Vector<Card>(), new Visibility(Facing.FACE_UP));

        Environment.getEnvironment().addNewDeck(decks[0], new GridPosition(0,-2));
        Environment.getEnvironment().addNewDeck(decks[1], new GridPosition(0, 2));
        Environment.getEnvironment().addNewDeck(piles[0], new GridPosition(0,-1));
        Environment.getEnvironment().addNewDeck(piles[1], new GridPosition(0, 1));
        this.initialized = true;
    }

    public int getVictor() {
        int possibleVictor = -1;

        for (int i = 0; i < decks.length; i++) {
            if (decks[i].size() != 0 || piles[i].size() != 0) {
                if (possibleVictor == -1) {
                    possibleVictor = i;
                } else {
                    // one other player with cards found
                    return -1;
                }
            }
        }

        return possibleVictor;
    }

    /**
     * advances the state of the game
     * @return true if the game is still going, false if the game has reached a terminating condition
     */
    public boolean advanceState(int player_id) {
        if (!initialized) {
            throw new IllegalStateException();
        }
        if (getVictor() != -1) {
            System.out.println("Game already finished");
            return false;
        }

        // flip over the card of this player
        if (piles[player_id].size() == 0) {
            System.out.println("flipping card for player " + player_id);
            Card top = decks[player_id].pop();
            if (top == null) {
                return false;
            }
            piles[player_id].addElement(top);
        } else {
            // player has already played
            System.out.println("Player has already played");
            return false;
        }

        // check to see if all players have played and we can advance to the next round
        for (int i = 0; i < piles.length; i++) {
            if (piles[i].size() == 0) {
                System.out.println("All players have not played yet.");
                return false;
            }
        }

        // delay so that everyone can see who won
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            Factory.log().e(e.toString());
        }

        // find out who the winner is
        int max_ind = 0;
        for (int i = 1; i < piles.length; i++) {
            if (compareCard(piles[max_ind].getTopCard(), piles[i].getTopCard()) < 0) {
                max_ind = i;
            }
        }

        System.out.println("player " + max_ind + " won this round");

        // add all the cards to the winners deck
        for (int i = 0; i < piles.length; i++) {
            decks[max_ind].addToBottom(piles[i].pop());
        }

        return getVictor() != -1;
    }

    public int compareCard(Card c1, Card c2) {
        int v1 = c1.rank.ordinal() * 4 + c1.suit.ordinal();
        int v2 = c2.rank.ordinal() * 4 + c2.suit.ordinal();
        return v1 - v2;
    }

    @Override
    public void handleTouch(Deck deck, Card card) {
        System.out.println("Handling touch!");
        if (deck == decks[current_player]) {
            boolean finished = advanceState(current_player);
            if (finished) {
                int winner = this.getVictor();
                Factory.log().notice("Player " + winner + " has won the game!");
            }
            try {
                JSONObject msg = new JSONObject();
                msg.put("type", "move");
                msg.put("user_id", "android_phone");
                msg.put("player_id", current_player);
                new ZeroMQSendThread(socket, msg).start();
            } catch (JSONException e) {
                Factory.log().e(e.toString());
            }
        }
    }

    @Override
    public void handleMessage(JSONObject msg) throws JSONException {
        String type = msg.getString("type");
        if (type.equals("init")) {
            System.out.println("Received init msg");
            // get the player_id
            int player_to_init = msg.getInt("player_id");

            // initialize the deck
            JSONArray j_cards = msg.getJSONArray("deck");
            Rank[] ranks = Rank.values();
            Suit[] suits = Suit.values();
            List<Card> cards = new Vector<Card>(52);
            for (int i = 0; i < j_cards.length(); i++) {
                int i_card = j_cards.getInt(i);
                Rank r = ranks[i_card / 4];
                Suit s = suits[i_card % 4];
                cards.add(new Card(r, s));
            }

            // now initialize the game
            this.init(player_to_init, cards);
        } else if (type.equals("move")) {
            int player_id = msg.getInt("player_id");
            System.out.println("Received move message from player_id " + player_id);
            if (player_id != current_player) {
                boolean finished = advanceState(player_id);
                if (finished) {
                    int winner = this.getVictor();
                    Factory.log().notice("Player " + winner + " has won the game!");
                }
            }
        } else {
            System.out.println("Unknown message type");
        }
    }
}
