package io.github.simcards.libcards.game;

public interface UserGame {
    /**
     * Called by the server to initialize the game
     * @param numPlayers the number of players to set the game up for
     */
    public void init(int numPlayers);

    /**
     * Advances the state of the game given input from the player
     * @param event input from the player about which cards were interacted with
     */
    public void advanceState(CardGameEvent event);

    /**
     * Returns the player_id of the winner, -1 if no winner yet
     * @return the player_id of the winner, -1 if no winner yet
     */
    public int getWinner();
}





