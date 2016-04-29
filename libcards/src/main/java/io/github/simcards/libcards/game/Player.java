package io.github.simcards.libcards.game;

import java.io.Serializable;

/**
 * Represents a player of the card game
 */
public class Player implements Serializable {

    // the player's unique identifier within the game
    public final int ID;

    /**
     * Constructs a player object with the given ID
     * @param ID the player's unique identifier within the game
     */
    public Player(int ID) {
        this.ID = ID;
    }
}
