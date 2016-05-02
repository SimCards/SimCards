package io.github.simcards.libcards.customgames;

import java.util.ArrayList;
import java.util.List;

import io.github.simcards.libcards.game.Card;
import io.github.simcards.libcards.game.CardGameEvent;
import io.github.simcards.libcards.game.Deck;
import io.github.simcards.libcards.game.Environment;
import io.github.simcards.libcards.game.UserGame;
import io.github.simcards.libcards.game.Visibility;
import io.github.simcards.libcards.game.enums.Arrangement;
import io.github.simcards.libcards.game.enums.Facing;
import io.github.simcards.libcards.game.enums.Rank;
import io.github.simcards.libcards.util.GridPosition;

/**
 * Represents a no-dealer game of blackjack.
 */
public class Blackjack implements UserGame {

    /** The deck that players draw from. */
    private Deck mainDeck;
    /** The hands of the players. */
    private Deck[] hands;
    /** Whether each player is finished drawing. */
    private boolean done[];
    /** Whether each player has displayed a hand. */
    private boolean displayed[];
    /** The ID of the winner of the game. */
    private int winner = -1;

    @Override
    public void init(int numPlayers) {
        // Set up the main deck.
        mainDeck = new Deck(Deck.getStandard52Cards(), new Visibility(Facing.FACE_DOWN));
        mainDeck.shuffle();
        hands = new Deck[numPlayers];
        done = new boolean[numPlayers];
        displayed = new boolean[numPlayers];
        Environment environment = Environment.getEnvironment();
        environment.addNewDeck(mainDeck, new GridPosition(0, 0));

        // Draw initial hands for each player.
        for (int i = 0; i < numPlayers; i++) {
            List<Card> initialHand = new ArrayList<>(5);
            for (int j = 0; j < 2; j++) {
                initialHand.add(mainDeck.pop());
            }
            hands[i] = new Deck(initialHand, i);
            environment.addHand(hands[i]);
        }
    }

    @Override
    public void advanceState(CardGameEvent event) {
        int playerID = event.getPlayer().ID;
        Deck playerHand = hands[playerID];
        if (!done[playerID]) {
            if (event.getDecks().contains(mainDeck)) {
                // Draw from the deck.
                playerHand.addElement(mainDeck.pop());
                if (playerHand.size() >= 5) {
                    done[playerID] = true;
                }
                if (getValue(playerHand.cards) > 21) {
                    done[playerID] = true;
                    displayed[playerID] = true;
                }
            } else {
                // Stand and draw no more cards.
                done[playerID] = true;
            }

            if (displayed[playerID]) {
                displayDeck(playerHand);
            }

            if (done[playerID]) {
                // Check if all players are done.
                for (int i = 0; i < done.length; i++) {
                    if (!done[i]) {
                        return;
                    }
                }

                // Display hands and end the game.
                int highestValue = 0;
                for (int i = 0; i < hands.length; i++) {
                    if (!displayed[i]) {
                        displayDeck(hands[i]);
                        displayed[i] = true;
                    }

                    // Check who has won the game.
                    int currentValue = getValue(hands[i].cards);
                    boolean tie = false;
                    if (currentValue <= 21 && currentValue > highestValue) {
                        winner = i;
                        highestValue = currentValue;
                        tie = false;
                    } else if (currentValue == highestValue) {
                        tie = true;
                    }

                    if (winner == -1) {
                        System.out.println("Everyone loses!");
                    } else if (tie) {
                        System.out.println("Tie game!");
                    } else {
                        System.out.println("Player " + winner + " wins!");
                    }
                }
            }
        }
    }

    /**
     * Displays a hand on the field.
     * @param hand The hand to display.
     */
    private void displayDeck(Deck hand) {
        Environment environment = Environment.getEnvironment();
        Deck displayedDeck = new Deck(hand.cards, new Visibility(Facing.FACE_UP, false, Arrangement.HORIZONTAL));
        int yPosition = hand.playerID == 0 ? -1 : 1;
        environment.addNewDeck(displayedDeck, new GridPosition(0, yPosition));
        environment.removeDeck(hand.id);
    }

    /**
     * Gets the value of a list of cards.
     * @param cards The cards to get a value for.
     * @return The value of the list of cards.
     */
    private int getValue(List<Card> cards) {
        boolean hasAce = false;
        int total = 0;
        for (Card card : cards) {
            int value = Math.min(card.rank.ordinal() + 1, 10);
            if (card.rank == Rank.ACE) {
                hasAce = true;
            }
            total += value;
        }
        if (hasAce && total <= 11) {
            total += 10;
        }
        return total;
    }

    @Override
    public int getWinner() {
        return winner;
    }
}
