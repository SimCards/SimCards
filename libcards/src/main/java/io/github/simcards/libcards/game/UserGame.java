package io.github.simcards.libcards.game;

import org.json.JSONObject;

import java.util.List;

public interface UserGame {
    /**
     * Called by the server to initialize the game
     * @param numPlayers the number of players to set the game up for
     */
    public void init(int numPlayers);

    /**
     * Advances the state of the game given a dictionary of input fields defined by the player
     * @param event input from the player about which cards were interacted with
     * @return true if the game is finished, false otherwise
     */
    public void advanceState(CardGameEvent event);

    /**
     * Returns the player_id of the winner
     * @return the player_id of the winner
     */
    public int getWinner();
}
