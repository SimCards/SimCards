package io.github.simcards.libcards.util;


import io.github.simcards.libcards.game.Card;

/**
 * Created by Vishal on 2/26/16.
 */
public class Middleman {
    static {}

    private static IMiddleman middleman;

    public static void init(IMiddleman middleman) {
        Middleman.middleman = middleman;
    }

    public static int getImageLocation(Card c) {
        return middleman.getImageLocation(c);
    }
}
