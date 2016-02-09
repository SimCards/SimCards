package io.github.simcards.simcards.game;

import android.opengl.Matrix;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

import io.github.simcards.simcards.client.graphics.GLRenderer;
import io.github.simcards.simcards.client.graphics.GraphicsUtil;
import io.github.simcards.simcards.util.BoundingBox;
import io.github.simcards.simcards.util.GridPosition;
import io.github.simcards.simcards.util.Position;
import io.github.simcards.simcards.util.RandomUtil;

/**
 * A group of cards.
 */
public class Deck {

    /** The cards in the deck. */
    private List<Card> cards;
    /** The grid position of the deck's base on the field. */
    public GridPosition gridPosition;
    /** The visibility of the cards in the deck. */
    public Visibility visibility;

    /**
     * Creates a deck of cards.
     * @param cards The cards initially in the deck.
     * @param visibility The visibility of the deck.
     */
    public Deck(List<Card> cards, GridPosition gridPosition, Visibility visibility) {
        this.cards = cards;
        this.gridPosition = gridPosition;
        this.visibility = visibility;
        int deckSize = cards.size();
        for (int i = 0; i < deckSize; i++) {
            Card card = cards.get(i);
            renderNextCard(card);
            if (visibility == Visibility.TOP_FACE_UP && i < deckSize - 1) {
                card.setFaceUp(false);
            }
        }
    }

    /**
     * Adds a card to the deck.
     * @param card The card to add to the deck.
     * @return Whether the card was successfully added to the deck.
     */
    public boolean addElement(Card card) {
        boolean success = cards.add(card);
        if (success) {
            renderNextCard(card);
            if (visibility == Visibility.TOP_FACE_UP) {
                Card prevTopCard = getOffsetTopCard(1);
                if (prevTopCard != null) {
                    prevTopCard.setFaceUp(false);
                }
            }
        }
        return success;
    }

    /**
     * Draws a card on the screen at the appropriate place on the top of the deck.
     * @param card The card to draw.
     */
    private void renderNextCard(Card card) {
        if (visibility == Visibility.FACE_DOWN) {
            card.setFaceUp(false);
        }
        Position worldPosition = gridPosition.getWorldPosition();
        card.createShape(worldPosition);
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
        } else if (cardIndex > 0) {
            cards.remove(card);
            card.removeShape();
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
            topCard.removeShape();
            if (visibility == Visibility.TOP_FACE_UP && !cards.isEmpty()) {
                getTopCard().setFaceUp(true);
            }
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
     * Does an action upon the deck being touched.
     * @param event The motion event to process the touch with.
     */
    public void touch(MotionEvent event) {
        // Convert the touch position to world coordinates.
        Position touchPosition = new Position(event.getX(), event.getY());
        float halfScreenHeight = GraphicsUtil.screenHeight / 2;
        touchPosition.addPosition(-GraphicsUtil.screenWidth / 2, -halfScreenHeight);
        touchPosition.invertY();
        touchPosition.scale(GLRenderer.sCamera.scale / halfScreenHeight);
        touchPosition.addPosition(GLRenderer.sCamera.position);
        if (getBoundingBox().isInside(touchPosition)) {
            // Process the deck touch.
            Card topCard = getTopCard();
            if (topCard != null) {
                System.out.println(topCard.rank.ordinal());
                System.out.println(topCard.suit.ordinal());
            }
        }
    }

    /**
     * Gets the bounding box around the deck.
     * @return The bounding box around the deck.
     */
    private BoundingBox getBoundingBox() {
        Position deckPosition = gridPosition.getWorldPosition();
        float xOffset = Card.getScaledCenterOffsetX();
        float yOffset = Card.getScaledCenterOffsetY();
        return new BoundingBox(deckPosition.x - xOffset, deckPosition.x + xOffset,
                deckPosition.y - yOffset, deckPosition.y + yOffset);
    }
}
