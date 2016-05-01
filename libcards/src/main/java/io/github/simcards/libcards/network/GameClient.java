package io.github.simcards.libcards.network;

import org.json.JSONException;
import org.json.JSONObject;
import org.zeromq.ZMQ;

import javax.smartcardio.Card;

import io.github.simcards.libcards.game.CardGameEvent;
import io.github.simcards.libcards.game.Deck;
import io.github.simcards.libcards.game.Game;
import io.github.simcards.libcards.game.UserGame;
import io.github.simcards.libcards.graphics.DeckView;
import io.github.simcards.libcards.graphics.GameScreen;
import io.github.simcards.libcards.util.GridPosition;

/**
 * Created by Vishal on 4/18/16.
 */
public class GameClient extends Thread {

    private ZMQ.Socket sock;
    private GameClientListener listener;

    /**
     * Constructs a game client with an unitialized game and a connected socket
     * @param sock the pair socket connected to the game server used for communication
     */
    public GameClient(ZMQ.Socket sock, GameClientListener listener) {
        this.sock = sock;
        this.listener = listener;
    }

    /**
     * Runs the game loop, updating the game in response to
     */
    public void run() {
        boolean game_over = false;
        while (!game_over) {
            byte[] msgBuf = sock.recv();
            SerializableMsg msg = SerializableMsg.fromBytes(msgBuf);
            switch (msg.type) {
                case GAME_OVER:
                    game_over = true;
                    break;
                case DECK_ADD: {
                    DeckUpdater.DeckAddMsg dam = (DeckUpdater.DeckAddMsg) msg.getContent();
                    GameScreen.getScreen().addNewDeck(new DeckView(dam.d, dam.p, dam.rotation));
                    break;
                }
                case DECK_REMOVE: {
                    int deckId = msg.getInt();
                    GameScreen.getScreen().removeDeck(deckId);
                    break;
                } case DECK_UPDATE: {
                    Deck d = msg.getDeck();
                    GameScreen.getScreen().getDeck(d.id).replaceDeck(d);
                    break;
                } default:
                    System.out.println("Unhandled message type: " + msg.type.toString());
                    break;
            }
        }
        listener.onGameOver();
    }

    public void handleInput(EventMessage event) {
        (new ZeroMQSendThread(sock, new SerializableMsg(MessageType.EVENT, event))).start();
    }

    public interface GameClientListener {
        void onGameOver();
    }

}
