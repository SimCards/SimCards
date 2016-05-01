package io.github.simcards.server;


import org.json.JSONException;
import org.json.JSONObject;
import org.zeromq.ZMQ;

import java.awt.Event;
import java.util.ArrayList;
import java.util.List;

import io.github.simcards.libcards.game.Card;
import io.github.simcards.libcards.game.CardGameEvent;
import io.github.simcards.libcards.game.Deck;
import io.github.simcards.libcards.game.Environment;
import io.github.simcards.libcards.game.Game;
import io.github.simcards.libcards.game.Player;
import io.github.simcards.libcards.game.UserGame;
import io.github.simcards.libcards.network.EventMessage;
import io.github.simcards.libcards.network.GameReadyMessage;
import io.github.simcards.libcards.network.MessageType;
import io.github.simcards.libcards.network.SendQueueThread;
import io.github.simcards.libcards.network.SerializableMsg;
import io.github.simcards.libcards.util.TouchHandler;

public class GameServer extends Thread {

    private Game gameContainer;
    private UserGame game;
    private ZMQ.Socket[] socks;

    public GameServer(Game game, ZMQ.Socket[] socks) {
        this.gameContainer = game;
        this.game = gameContainer.game;
        this.socks = socks;
        SendQueueThread.init(socks);
    }

    @Override
    public void run() {
        Environment.getEnvironment().registerTouchHandler(new TouchHandler() {
            @Override
            public void handleTouch(int player_id, Deck deck, Card card) {
                // actually figure out the player's id
                Player player = new Player(player_id);
                List<Deck> decks = new ArrayList<>(1);
                decks.add(deck);
                List<List<Card>> cards = new ArrayList<>(1);
                List<Card> nestedCards = new ArrayList<>(1);
                nestedCards.add(card);
                cards.add(nestedCards);
                CardGameEvent event = new CardGameEvent(player, decks, cards);
                game.advanceState(event);
            }
        });

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
                            System.out.println("Received card game event of type " + msg.type + " from socket " + i);
                            EventMessage eventMessage = (EventMessage) msg.getContent();
                            Environment.getEnvironment().touchDeck(eventMessage.getPlayerId(), eventMessage.getDeckIds().get(0), eventMessage.getCardIds().get(0).get(0));
                            break;
                        default:
                            System.out.println("unhandled message type " + msg.type.toString());
                    }
                }
            }
        }

        // cleanup
        System.out.println("sending game_over message");
        sendAll(new SerializableMsg(MessageType.GAME_OVER, game.getWinner()));

        boolean allSent = false;
        while (!allSent) {
            try {
                SendQueueThread.getThread().join();
                allSent = false;
            } catch (InterruptedException e) {
                System.out.println("something interrupted sendqueuethread join");
            }
        }

        System.out.println("Closing sockets and cleaning up");
        for (int i = 0; i < socks.length; i++) {
            poller.unregister(socks[i]);
            socks[i].close();
        }
        System.out.println("Game server all cleaned up!");
    }

    private void sendAll(SerializableMsg msg) {
        SendQueueThread.getThread().addToQueue(msg);
    }

}
