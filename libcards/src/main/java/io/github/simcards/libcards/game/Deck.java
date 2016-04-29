package io.github.simcards.libcards.game;

import java.util.ArrayList;
import java.util.List;

import io.github.simcards.libcards.game.enums.Arrangement;
import io.github.simcards.libcards.game.enums.Facing;
import io.github.simcards.libcards.game.enums.Rank;
import io.github.simcards.libcards.game.enums.Suit;
import io.github.simcards.libcards.graphics.GameScreen;
import io.github.simcards.libcards.util.RandomUtil;

/**
 * A group of cards.
 */
public class Deck {

    /** The ID of the deck, used to sync it with client-side DeckViews. */
    public final int id;
    /** The cards in the deck. */
    public List<Card> cards;
    /** The visibility settings of the cards in the deck. */
    public Visibility visibility;
    /** The ID of the player who owns the deck. */
    public int playerID = -1;

    /** Counter used to assign unique deck IDs. */
    private static int idCounter;

    /**
     * Creates a deck of cards.
     * @param cards The cards initially in the deck.
     * @param visibility The visibility settings of the deck.
     */
    public Deck(List<Card> cards, Visibility visibility) {
        this.id = idCounter++;
        this.cards = cards;
        this.visibility = visibility;
    }

    /**
     * Creates a hand of cards.
     * @param cards The cards initially in the hand.
     * @param playerID The ID of the player who owns the hand.
     */
    public Deck(List<Card> cards, int playerID) {
        this(cards, new Visibility(Facing.OWNER, false, Arrangement.HORIZONTAL));
        this.playerID = playerID;
    }

    /**
     * Adds a card to the deck.
     * @param card The card to add to the deck.
     * @return Whether the card was successfully added to the deck.
     */
    public boolean addElement(Card card) {
        boolean success = cards.add(card);
        if (success) {
            // TODO: Change to a packet.
            GameScreen.getScreen().getDeck(id).redraw();
        }
        return success;
    }

    /**
     * Adds a card to the bottom of the deck.
     * @param card The card to add to the deck.
     */
    public void addToBottom(Card card) {
        cards.add(0, card);
    }

    /**
     * Removes a card from the deck.
     * @param card The card to remove.
     * @return The removed card, or null if the card was not in the deck to begin with.
     */
    public Card remove(Card card) {
        int cardIndex = cards.indexOf(card);
        if (cardIndex == cards.size() - 1) {
            return pop();
        } else if (cardIndex >= 0) {
            cards.remove(card);
            // TODO: Change to a packet.
            GameScreen.getScreen().getDeck(id).redraw();
            return card;
        } else {
            return null;
        }
    }

    /**
     * Gets the card on the top of the deck.
     * @return The card on the top of the deck, or null if the deck is empty.
     */
    public Card getTopCard() {
        if (cards.isEmpty()) {
            return null;
        } else {
            return cards.get(cards.size() - 1);
        }
    }

    /**
     * Gets the card at a certain position offset from the top of the deck.
     * @param offset The offset to find a card with.
     * @return The card at the offset position,
     * or null if the deck doesn't have enough cards for the offset.
     */
    public Card getOffsetTopCard(int offset) {
        int deckPosition = cards.size() - 1 - offset;
        if (deckPosition < 0) {
            return null;
        } else {
            return cards.get(deckPosition);
        }
    }

    /**
     * Removes the top card from the deck.
     * @return The removed card, or null if the deck is empty.
     */
    public Card pop() {
        if (cards.isEmpty()) {
            return null;
        } else {
            Card topCard = cards.remove(cards.size() - 1);
            GameScreen.getScreen().getDeck(id).redraw();
            return topCard;
        }
    }

    /**
     * Shuffles the order of the cards in the deck.
     */
    public void shuffle() {
        List<Card> shuffled = new ArrayList<>(cards.size());
        while (!cards.isEmpty()) {
            shuffled.add(RandomUtil.removeRandomElementInList(cards));
        }
        cards = shuffled;
    }

    /**
     * returns a list of the standard 52 cards in order
     * @return a list of the standard 52 cards in order
     */
    public static List<Card> getStandard52Cards() {
        Rank[] ranks = Rank.values();
        Suit[] suits = Suit.values();

        List<Card> cards = new ArrayList<>(52);
        for (int i = 0; i < 52; i++) {
            Rank r = ranks[i / 4];
            Suit s = suits[i % 4];
            cards.add(new Card(r, s));
        }

        return cards;
    }

    /**
     * returns the number of cards in the deck
     * @return the number of  cards in the deck
     */
    public int size() {
        return cards.size();
    }

    /**
     * Checks if the deck is empty.
     * @return Whether the deck is empty.
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
