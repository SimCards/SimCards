package io.github.simcards.libcards.customgames;

import java.util.List;
import java.util.Vector;

import io.github.simcards.libcards.game.Card;
import io.github.simcards.libcards.game.CardGameEvent;
import io.github.simcards.libcards.game.Deck;
import io.github.simcards.libcards.game.Environment;
import io.github.simcards.libcards.game.UserGame;
import io.github.simcards.libcards.game.Visibility;
import io.github.simcards.libcards.game.enums.Facing;
import io.github.simcards.libcards.util.GridPosition;
import io.github.simcards.libcards.util.Factory;

/**
 * Created by Vishal on 2/12/16.
 */
public class AbsolutelyRankedWarGame implements UserGame {

    private Deck[] decks;
    private Deck[] piles;

    private boolean initialized;

    @Override
    public void init(int numPlayers) {
        Deck initial = new Deck(Deck.getStandard52Cards(), null);
        // initial.shuffle();

        decks = new Deck[2];
        piles = new Deck[2];

        List<Card> deck1 = new Vector<>(26);
        List<Card> deck2 = new Vector<>(26);

        for (int i = 0; i < 26; i++) {
            deck1.add(initial.getOffsetTopCard(i));
            deck2.add(initial.getOffsetTopCard(i+26));
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

    @Override
    public int getWinner() {
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
     */
    @Override
    public void advanceState(CardGameEvent event) {
        int player_id = event.getPlayer().ID;
        if (!initialized) {
            throw new IllegalStateException();
        }
        if (getWinner() != -1) {
            System.out.println("Game already finished");
            return;
        }

        // flip over the card of this player
        if (piles[player_id].size() == 0) {
            System.out.println("flipping card for player " + player_id);
            Card top = decks[player_id].pop();
            if (top == null) {
                return;
            }
            piles[player_id].addElement(top);
        } else {
            // player has already played
            System.out.println("Player has already played");
            return;
        }

        // check to see if all players have played and we can advance to the next round
        for (int i = 0; i < piles.length; i++) {
            if (piles[i].size() == 0) {
                System.out.println("All players have not played yet.");
                return;
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
    }

    private int compareCard(Card c1, Card c2) {
        int v1 = c1.rank.ordinal() * 4 + c1.suit.ordinal();
        int v2 = c2.rank.ordinal() * 4 + c2.suit.ordinal();
        return v1 - v2;
    }

}
