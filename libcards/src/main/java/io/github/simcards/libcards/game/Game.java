package io.github.simcards.libcards.game;

import com.sun.corba.se.spi.orbutil.fsm.Input;

import org.json.JSONObject;

import io.github.simcards.libcards.network.MessageHandler;
import io.github.simcards.libcards.util.InputHandler;
import io.github.simcards.libcards.util.TouchHandler;

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
