package io.github.simcards.server;

import org.zeromq.ZMQ;

import java.net.UnknownHostException;

import io.github.simcards.libcards.customgames.AbsolutelyRankedWarGame;
import io.github.simcards.libcards.customgames.Blackjack;
import io.github.simcards.libcards.customgames.CardPickup;
import io.github.simcards.libcards.game.Game;
import io.github.simcards.libcards.game.GameInfo;
import io.github.simcards.libcards.game.UserGame;

public class GameServerThread extends Thread {

    String gameId;
    int numPlayers;
    ZMQ.Context ctx;
    GameServerConnector connector;

    public GameServerThread(String gameId, int numPlayers) throws UnknownHostException {
        this.gameId = gameId;
        this.numPlayers = numPlayers;
        this.ctx = ZMQ.context(1);
        this.connector = new GameServerConnector(ctx, numPlayers);
    }

    public String[] getPlayerSocketAddrs() {
        return this.connector.getPlayerSockAddrs();
    }

    @Override
    public void run() {
        // wait for everyone to connect
        System.out.println("Waiting for everyone to connect");
        ZMQ.Socket[] socks = connector.connectAll();

        // start up the game server
        System.out.println("starting up game server");
        Game game = downloadGame(gameId);
        GameServer gameServer = new GameServer(game, socks);
        gameServer.start();
    }

    private static Game downloadGame(String gameId) {
        // TODO: actually download the correct game
        UserGame game;
        //game = new AbsolutelyRankedWarGame();
        //game = new Blackjack();
        game = new CardPickup();
        return new Game(game, new GameInfo());
    }
}
