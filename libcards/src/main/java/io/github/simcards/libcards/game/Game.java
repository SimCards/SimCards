package io.github.simcards.libcards.game;

/**
 * Created by Vishal on 4/16/16.
 */
public class Game {

    private GameInfo gameInfo;
    public UserGame game;

    public Game(UserGame game, GameInfo gameInfo) {
        this.game = game;
        this.gameInfo = gameInfo;
    }

    /**
     * Returns information about the game (name, description, etc.)
     * @return information about the game (name, description, etc.)
     */
    public GameInfo getGameInfo() {
        return gameInfo;
    }
}
