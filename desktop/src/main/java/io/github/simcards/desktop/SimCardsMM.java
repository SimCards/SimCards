package io.github.simcards.desktop;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.Animator;

import org.zeromq.ZMQ;

import java.util.Set;

import io.github.simcards.libcards.graphics.GLRenderer;
import io.github.simcards.libcards.graphics.GameScreen;
import io.github.simcards.libcards.graphics.GraphicsUtil;
import io.github.simcards.libcards.network.EventMessage;
import io.github.simcards.libcards.network.GameClient;
import io.github.simcards.libcards.network.MatchmakingClient;
import io.github.simcards.libcards.util.ClientTouchHandler;

public class SimCardsMM {

    public static final String MM_SERVER_ADDR = "143.215.90.209";
    public static String gameId = "";

    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                System.out.println("Terminating ZMQ socket");
                ZMQ.context(1).term();
            }
        });

        System.out.println("Starting matchmaking client!");
        // start the matchmaking client
        MatchmakingClient mmClient = new MatchmakingClient(MM_SERVER_ADDR, gameId, new MyMMListener());
        mmClient.start();
    }

    public static void startGameClient(ZMQ.Socket sock, final int playerId) {
        SimCardsDesktop.renderer = new GLRenderer();

        GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2ES2));
        GLWindow glWindow = GLWindow.create(caps);

        SimCardsDesktop.animator = new Animator(glWindow);
        glWindow.addGLEventListener(new GLEventListenerDesktop());

        KeyboardListener keyboard = new KeyboardListener();
        glWindow.addKeyListener(keyboard);

        MouseListener mouse = new MouseListener();
        glWindow.addMouseListener(mouse);

        glWindow.setTitle("SimCards");
        GraphicsUtil.screenWidth = 350;
        GraphicsUtil.screenHeight = 700;
        glWindow.setSize(GraphicsUtil.screenWidth, GraphicsUtil.screenHeight);
        glWindow.setVisible(true);
        SimCardsDesktop.animator.start();

        final GameClient gameClient = new GameClient(sock, new GameClient.GameClientListener() {
            @Override
            public void onGameOver() {
                System.out.println("Game Over");
            }
        });

        GameScreen.getScreen().setTouchHandler(new ClientTouchHandler() {
            @Override
            public void onTouched(int deckId, int cardId) {
                EventMessage eventMsg = new EventMessage(playerId, deckId, cardId);
                gameClient.handleInput(eventMsg);
            }
        });

        gameClient.start();
    }


    public static class MyMMListener implements MatchmakingClient.MMListener {

        int numPlayersConnected;

        @Override
        public void onConnected(Set<Integer> playersConnected) {
            numPlayersConnected = playersConnected.size();
            System.out.println(numPlayersConnected + " player(s) connected");
        }

        @Override
        public void onSuccess(ZMQ.Socket sock, int player_id) {
            System.out.println("Matchmaking Success!");
            startGameClient(sock, player_id);
        }

        @Override
        public void onFailure(String message) {
            System.out.println("Matchmaking failed!");
        }
    }

}
