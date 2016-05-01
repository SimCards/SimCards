package io.github.simcards.libcards.graphics;

import io.github.simcards.libcards.game.Card;
import io.github.simcards.libcards.game.Deck;
import io.github.simcards.libcards.util.GridPosition;
import io.github.simcards.libcards.util.Position;

/**
 * Client-side representation of a hand.
 */
public class HandView extends DeckView {

    /**
     * Initializes a hand view.
     * @param deck The cards initially in the hand.
     * @param playerID The ID of the player who owns the hand.
     */
    public HandView(Deck deck, int playerID) {
        super(deck);
        gridPosition = new GridPosition(0, 0);
        rotation = 0;
        this.playerID = playerID;
        redraw();
    }

    @Override
    public void redraw() {
        // Calculate the distance between cards.
        if (!deck.isEmpty()) {
            int deckSize = deck.size();
            float cardWidth = CardShape.CARD_WIDTH;
            float totalCardDistance = deckSize * cardWidth;
            float excessWidth = totalCardDistance - GraphicsUtil.getWorldScreenWidth();
            cardDistanceMultiplier = 1;
            if (excessWidth > 0) {
                cardDistanceMultiplier -= excessWidth / (deckSize - 1) / cardWidth;
            }
        }
        super.redraw();
    }

    @Override
    protected CardShape createCardShape(Card card, Position position, float rotation) {
        return new CardShape(card, position.clone(), rotation, true);
    }

    @Override
    protected Position getCurrentPosition() {
        float rightOffset = 0;
        if (cardDistanceMultiplier == 1) {
            rightOffset = -(deck.size() - 1) * CardShape.getCenterOffsetX();
        } else {
            rightOffset = -GraphicsUtil.getWorldScreenWidth() / 2 + CardShape.getCenterOffsetX();
        }
        return new Position(rightOffset, CardShape.getCenterOffsetY() - 1);
    }
}
