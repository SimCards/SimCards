package io.github.simcards.libcards.util;

import io.github.simcards.libcards.graphics.CardShape;

/**
 * Used to get platform-specific card image resources.
 * Created by Vishal on 2/26/16.
 */
public interface IMiddleman {
    /**
     * Gets the location of a card resource.
     * @param c The card to get an image for.
     * @return The location of the image representing the card.
     */
    int getImageLocation(CardShape c);
}
