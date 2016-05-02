package io.github.simcards.libcards.customgames;

import java.util.ArrayList;
import java.util.List;

import io.github.simcards.libcards.game.Card;
import io.github.simcards.libcards.game.CardGameEvent;
import io.github.simcards.libcards.game.Deck;
import io.github.simcards.libcards.game.Environment;
import io.github.simcards.libcards.game.UserGame;
import io.github.simcards.libcards.game.Visibility;
import io.github.simcards.libcards.game.enums.Facing;
import io.github.simcards.libcards.util.GridPosition;
import io.github.simcards.libcards.util.RandomUtil;

/**
 * 52-card pickup.
 */
public class CardPickup implements UserGame {

    /** The piles on the field. */
    private List<Deck> piles;
    /** The number of cards collected by each player. */
    private int[] cardsCollected;
    /** The winner of the game. */
    private int winner = -1;

    /** The range of the piles. */
    private static final int SPREAD = 10;

    @Override
    public void init(int numPlayers) {
        Deck deck = new Deck(Deck.getStandard52Cards(), new Visibility(Facing.FACE_UP));
        deck.shuffle();
        int numCards = deck.size();
        piles = new ArrayList<>(numCards);
        Deck[][] positions = new Deck[SPREAD][SPREAD];
        for (int i = 0; i < numCards; i++) {
            Card card = deck.pop();
            int x = RandomUtil.getRandomNumberInRange(0, SPREAD - 1);
            int y = RandomUtil.getRandomNumberInRange(0, SPREAD - 1);
            if (positions[x][y] == null) {
                List<Card> cardList = new ArrayList<Card>();
                cardList.add(card);
                Deck newPile = new Deck(cardList, new Visibility(Facing.FACE_UP));
                piles.add(newPile);
                positions[x][y] = newPile;
            } else {
                positions[x][y].addElement(card);
            }
        }

        Environment environment = Environment.getEnvironment();
        int halfSpread = SPREAD / 2;
        for (int i = 0; i < SPREAD; i++) {
            for (int j = 0; j < SPREAD; j++) {
                if (positions[i][j] != null) {
                    environment.addNewDeck(positions[i][j], new GridPosition(i - halfSpread, j - halfSpread));
                }
            }
        }

        cardsCollected = new int[numPlayers];
    }

    @Override
    public void advanceState(CardGameEvent event) {
        List<Deck> eventDecks = event.getDecks();
        Deck pile = eventDecks.isEmpty() ? null : eventDecks.get(0);
        if (pile != null) {
            pile.pop();
            if (pile.isEmpty()) {
                Environment.getEnvironment().removeDeck(pile.id);
                piles.remove(pile);
            }
            cardsCollected[event.getPlayer().ID]++;
        }

        if (piles.isEmpty()) {
            int maxCards = -1;
            boolean tie = false;
            for (int i = 0; i < cardsCollected.length; i++) {
                if (cardsCollected[i] > maxCards) {
                    winner = i;
                    maxCards = cardsCollected[i];
                    tie = false;
                } else if (cardsCollected[i] == maxCards) {
                    tie = true;
                }
            }
            if (tie) {
                System.out.println("Tie game!");
            } else {
                System.out.println("Player " + winner + " wins!");
            }
        }
    }

    @Override
    public int getWinner() {
        return winner;
    }
}
