package io.github.simcards.server;


import org.json.JSONException;
import org.json.JSONObject;
import org.zeromq.ZMQ;

import io.github.simcards.libcards.game.CardGameEvent;
import io.github.simcards.libcards.game.Game;
import io.github.simcards.libcards.game.UserGame;
import io.github.simcards.libcards.network.GameReadyMessage;
import io.github.simcards.libcards.network.MessageType;
import io.github.simcards.libcards.network.SerializableMsg;

public class GameServer extends Thread {

    private Game gameContainer;
    private UserGame game;
    private ZMQ.Socket[] socks;

    public GameServer(Game game, ZMQ.Socket[] socks) {
        this.gameContainer = game;
        this.game = gameContainer.game;
        this.socks = socks;
    }

    @Override
    public void run() {
        this.game.init(socks.length);

        // init poller
        ZMQ.Poller poller = new ZMQ.Poller(socks.length);
        for (int i = 0; i < socks.length; i++) {
            poller.register(socks[i], ZMQ.Poller.POLLIN);
        }

        // game loop
        while (game.getWinner() < 0) {
            poller.poll();
            for (int i = 0; i < socks.length; i++) {
                if (poller.pollin(i)) {
                    SerializableMsg msg = SerializableMsg.fromBytes(socks[i].recv());
                    switch (msg.type) {
                        case EVENT:
                            CardGameEvent cge = msg.getCardGameEvent();
                            game.advanceState(cge);
                            break;
                        default:
                            System.out.println("unhandled message type " + msg.type.toString());
                    }
                }
            }
        }

        // cleanup
        sendAll(new SerializableMsg(MessageType.GAME_OVER, game.getWinner()));

        for (int i = 0; i < socks.length; i++) {
            poller.unregister(socks[i]);
            socks[i].close();
        }
    }

    private void sendAll(SerializableMsg msg) {
        for (int i = 0; i < socks.length; i++) {
            socks[i].send(msg.getBytes());
        }
    }

}
