package io.github.simcards.libcards.util;

import io.github.simcards.libcards.graphics.CardShape;

/**
 * Created by Vishal on 2/26/16.
 */
public class Middleman {
    /**
     * Private constructor that prevents this class from being instantiated.
     */
    private Middleman() {
    }

    /** The middleman resource loader used by the platform. */
    private static IMiddleman middleman;

    /**
     * Sets the middleman resource loader used by the platform.
     * @param middleman The middleman resource loader used by the platform.
     */
    public static void init(IMiddleman middleman) {
        Middleman.middleman = middleman;
    }

    /**
     * Gets the location of a card resource.
     * @param c The card to get an image for.
     * @return The location of the image representing the card.
     */
    public static int getImageLocation(CardShape c) {
        return middleman.getImageLocation(c);
    }
}
