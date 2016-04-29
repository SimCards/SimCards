package io.github.simcards.libcards.network;

import java.io.Serializable;

public class GameReadyMessage implements Serializable {
    public final int playerID;
    public final int numPlayers;

    public GameReadyMessage(int playerID, int numPlayers) {
        this.playerID = playerID;
        this.numPlayers = numPlayers;
    }
}
