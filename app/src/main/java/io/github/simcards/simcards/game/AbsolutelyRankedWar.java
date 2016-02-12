package io.github.simcards.simcards.game;

import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import io.github.simcards.simcards.util.GridPosition;

/**
 * Created by Vishal on 2/12/16.
 */
public class AbsolutelyRankedWar {

    private Deck[] decks;
    private Deck[] piles;

    private boolean game_over;

    public AbsolutelyRankedWar(List<Card> cards) {
        decks = new Deck[2];
        piles = new Deck[2];

        decks[0] = new Deck(cards.subList(0, cards.size()/2), new GridPosition(0,-2), Visibility.FACE_DOWN);
        piles[0] = new Deck(new ArrayList<Card>(), new GridPosition(0,-1), Visibility.FACE_UP);

        decks[1] = new Deck(cards.subList(cards.size()/2, cards.size()), new GridPosition(0, 2), Visibility.FACE_DOWN);
        piles[1] = new Deck(new ArrayList<Card>(), new GridPosition(0, 1), Visibility.FACE_UP);

        Environment.getEnvironment().addNewDeck(decks[0]);
        Environment.getEnvironment().addNewDeck(decks[1]);
        Environment.getEnvironment().addNewDeck(piles[0]);
        Environment.getEnvironment().addNewDeck(piles[1]);
    }

    public int getVictor() {
        int possibleVictor = -1;

        for (int i = 0; i < decks.length; i++) {
            if (decks[i].size() != 0 && piles[i].size() != 0) {
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
        if (getVictor() == -1) {
            return false;
        }

        // flip over the card of this player
        if (piles[player_id].size() == 0) {
            Card top = decks[player_id].pop();
            piles[player_id].addElement(top);
        } else {
            // player has already played
            System.out.println("Player has already played");
            return false;
        }

        // check to see if all players have played and we can advance to the next round
        for (int i = 0; i < piles.length; i++) {
            if (piles[i].size() == 0) {
                return false;
            }
        }

        // delay so that everyone can see who won
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            Log.e("SimCards", e.toString());
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
            decks[max_ind].addElement(piles[i].pop());
        }

        return getVictor() != -1;
    }

    public int compareCard(Card c1, Card c2) {
        int v1 = c1.rank.ordinal() * 4 + c1.suit.ordinal();
        int v2 = c2.rank.ordinal() * 4 + c2.suit.ordinal();
        return v1 - v2;
    }
}
