package io.github.simcards.libcards.graphics;

import java.util.ArrayList;
import java.util.List;

import io.github.simcards.libcards.game.Card;
import io.github.simcards.libcards.game.Deck;
import io.github.simcards.libcards.game.enums.Arrangement;
import io.github.simcards.libcards.game.enums.Facing;
import io.github.simcards.libcards.util.GridPosition;
import io.github.simcards.libcards.util.Matrix;
import io.github.simcards.libcards.util.Position;

/**
 * Client-side representation of a deck.
 */
public class DeckView {

    /** The deck represented by this view. */
    private Deck deck;
    /** The card shapes on the field that are in the deck. */
    private List<CardShape> cardShapes = new ArrayList<>();
    /** The ID of the deck, used to sync it with server-side Decks. */
    final int id;
    /** The ID of the player who owns this deck. */
    // TODO: Figure out how players are tracked.
    final int playerID = -1;
    /** The grid position of the deck's base on the field. */
    private GridPosition gridPosition;
    /** The rotation angle of the deck. */
    private float rotation;
    /** Text displaying the number of cards in the deck. */
    private NumberHolder number;

    /**
     * Initializes a deck view.
     * @param deck The deck represented by this view.
     * @param position The grid position of the deck on the field.
     * @param rotation The rotation angle (in degrees) of the deck.
     */
    public DeckView(Deck deck, GridPosition position, float rotation) {
        this.deck = deck;
        id = deck.id;
        gridPosition = position;
        this.rotation = rotation;
        redraw();
    }

    /**
     * Redraws the deck when it is modified.
     */
    public void redraw() {
        for (CardShape shape : cardShapes) {
            shape.removeShape();
        }
        cardShapes.clear();
        int deckSize = deck.cards.size();
        Position currentPosition = gridPosition.getWorldPosition();
        int i = 0;
        if (deck.visibility.arrangement == Arrangement.STACKED) {
            i = deckSize - 1;
        }
        for (; i < deckSize; i++) {
            Card card = deck.cards.get(i);
            CardShape shape = new CardShape(card, currentPosition.clone(), rotation);
            if (deck.visibility.facing == Facing.FACE_DOWN ||
                    deck.visibility.facing == Facing.TOP_FACE_UP && i < deckSize - 1 ||
                    deck.visibility.facing == Facing.OWNER && playerID != GameScreen.getScreen().playerID) {
                shape.setFaceUp(false);
            } else {
                shape.setFaceUp(true);
            }
            cardShapes.add(shape);
            if (i < deckSize - 1) {
                if (deck.visibility.arrangement == Arrangement.HORIZONTAL) {
                    currentPosition.addX(CardShape.CARD_WIDTH * 0.2f);
                } else if (deck.visibility.arrangement == Arrangement.VERTICAL) {
                    currentPosition.addY(-CardShape.CARD_HEIGHT * 0.2f);
                }
            }
        }
        if (deck.visibility.hasCounter) {
            if (number != null) {
                number.remove();
            }
            if (deckSize > 0) {
                Position numberOffset = new Position(-CardShape.getCenterOffsetX() + NumberShape.NUMBER_WIDTH,
                        -CardShape.getCenterOffsetY() + NumberShape.NUMBER_HEIGHT);

                currentPosition.addPosition(numberOffset);
                number = new NumberHolder(deckSize, currentPosition, rotation);
                number.render();
            }
        }
    }

    /**
     * Checks whether the deck is being touched.
     * @param touchPosition The position where the screen was touched.
     * @return Whether the deck is being touched.
     */
    int getTouched(Position touchPosition) {
        for (int i = cardShapes.size() - 1; i >= 0; i--) {
            CardShape card = cardShapes.get(i);
            if (card.isTouched(touchPosition)) {
                return card.id;
            }
            if (deck.visibility.arrangement == Arrangement.STACKED) {
                return -1;
            }
        }
        return -1;
    }
}
