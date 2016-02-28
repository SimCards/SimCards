package io.github.simcards.libcards.graphics;

import io.github.simcards.libcards.game.Card;
import io.github.simcards.libcards.game.Deck;
import io.github.simcards.libcards.game.Visibility;
import io.github.simcards.libcards.util.BoundingBox;
import io.github.simcards.libcards.util.GridPosition;
import io.github.simcards.libcards.util.Position;

/**
 * Client-side representation of a deck.
 */
public class DeckView {

    /** The deck represented by this view. */
    private Deck deck;
    /** The ID of the deck, used to sync it with server-side Decks. */
    final int id;
    /** The grid position of the deck's base on the field. */
    private GridPosition gridPosition;
    /** Text displaying the number of cards in the deck. */
    private NumberHolder number;

    /**
     * Initializes a deck view.
     * @param deck The deck represented by this view.
     * @param position The grid position of the deck on the field.
     */
    public DeckView(Deck deck, GridPosition position) {
        this.deck = deck;
        id = deck.id;
        gridPosition = position;
        int deckSize = deck.cards.size();
        for (int i = 0; i < deckSize; i++) {
            Card card = deck.cards.get(i);
            CardShape shape = renderNextCard(card);
            if (deck.visibility == Visibility.TOP_FACE_UP && i < deckSize - 1) {
                shape.setFaceUp(false);
            }
        }
    }

    /**
     * Adds a card to the deck.
     * @param card The card to add to the deck.
     */
    public void addElement(Card card) {
        renderNextCard(card);
        if (deck.visibility == Visibility.TOP_FACE_UP) {
            Card prevTopCard = deck.getOffsetTopCard(1);
            if (prevTopCard != null) {
                CardShape shape = GameScreen.getScreen().getCard(prevTopCard.id);
                shape.setFaceUp(false);
            }
        }
    }

    /**
     * Removes a card from the deck.
     * @param card The card to remove from the deck.
     */
    public void remove(Card card) {
        CardShape shape = GameScreen.getScreen().getCard(card.id);
        shape.removeShape();
    }

    /**
     * Removes the top card from the deck and reveals the next card.
     * @param topCard The card to remove from the deck.
     */
    public void pop(Card topCard) {
        remove(topCard);
        if (deck.visibility == Visibility.TOP_FACE_UP && !deck.cards.isEmpty()) {
            CardShape shape = GameScreen.getScreen().getCard(deck.getTopCard().id);
            shape.setFaceUp(true);
        }
    }

    /**
     * Draws a card on the screen at the appropriate place on the top of the deck.
     * @param card The card to draw.
     * @return The card shape used to draw the card.
     */
    private CardShape renderNextCard(Card card) {
        Position worldPosition = gridPosition.getWorldPosition();
        CardShape shape = new CardShape(card, worldPosition);
        if (deck.visibility == Visibility.FACE_DOWN) {
            shape.setFaceUp(false);
        } else {
            shape.setFaceUp(true);
        }
        GameScreen.getScreen().addCard(shape);
        return shape;
    }

    /**
     * Checks whether the deck is being touched.
     * @param touchPosition The position where the screen was touched.
     * @return Whether the deck is being touched.
     */
    boolean isTouched(Position touchPosition) {
        // Convert the touch position to world coordinates.
        float halfScreenHeight = GraphicsUtil.screenHeight / 2;
        Position position = touchPosition.clone();
        position.addPosition(-GraphicsUtil.screenWidth / 2, -halfScreenHeight);
        position.invertY();
        position.scale(GLRenderer.camera.scale / halfScreenHeight);
        position.addPosition(GLRenderer.camera.position);
        BoundingBox boundingBox = getBoundingBox();
        return boundingBox.isInside(position);
    }

    /**
     * Gets the bounding box around the deck.
     * @return The bounding box around the deck.
     */
    private BoundingBox getBoundingBox() {
        Position deckPosition = gridPosition.getWorldPosition();
        float xOffset = CardShape.getCenterOffsetX();
        float yOffset = CardShape.getCenterOffsetY();
        return new BoundingBox(deckPosition.x - xOffset, deckPosition.x + xOffset,
                deckPosition.y - yOffset, deckPosition.y + yOffset);
    }
}
