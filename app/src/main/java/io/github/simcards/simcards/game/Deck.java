package io.github.simcards.simcards.game;

import java.util.ArrayList;
import java.util.List;

import io.github.simcards.simcards.util.Position;
import io.github.simcards.simcards.util.RandomUtil;

/**
 * A group of cards.
 */
public class Deck {

    /** The cards in the deck. */
    private List<Card> cards;
    /** The position of the deck's base on the field. */
    public Position position;
    /** The visibility of the cards in the deck. */
    public Visibility visibility;

    /**
     * Creates a deck of cards.
     * @param cards The cards initially in the deck.
     * @param visibility The visibility of the deck.
     */
    public Deck(List<Card> cards, Position position, Visibility visibility) {
        this.cards = cards;
        for (Card card : cards) {
            card.createShape(position);
        }
        this.position = position;
        this.visibility = visibility;
    }

    /**
     * Adds a card to the deck.
     * @param card The card to add to the deck.
     * @return Whether the card was successfully added to the deck.
     */
    public boolean addElement(Card card) {
        boolean success = cards.add(card);
        if (success) {
            card.createShape(position);
        }
        return success;
    }

    /**
     * Removes a card from the deck.
     * @param card The card to remove.
     * @return The removed card.
     */
    public Card remove(Card card) {
        cards.remove(card);
        card.removeShape();
        return card;
    }

    /**
     * Removes the top card from the deck.
     * @return The removed card.
     */
    public Card pop() {
        Card topCard = cards.remove(cards.size() - 1);
        topCard.removeShape();
        return topCard;
    }

    /**
     * Shuffles the order of the cards in the deck.
     */
    public void shuffle() {
        List<Card> shuffled = new ArrayList<Card>(cards.size());
        while (!cards.isEmpty()) {
            shuffled.add(RandomUtil.removeRandomElementInList(cards));
        }
        cards = shuffled;
    }
}
