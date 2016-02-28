package io.github.simcards.desktop;


import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.Animator;

import org.zeromq.ZMQ;

import java.util.ArrayList;
import java.util.List;

import io.github.simcards.libcards.game.AbsolutelyRankedWar;
import io.github.simcards.libcards.graphics.GLRenderer;
import io.github.simcards.libcards.graphics.GraphicsUtil;
import io.github.simcards.libcards.game.Card;
import io.github.simcards.libcards.game.Deck;
import io.github.simcards.libcards.game.Environment;
import io.github.simcards.libcards.game.enums.Rank;
import io.github.simcards.libcards.game.enums.Suit;
import io.github.simcards.libcards.game.Visibility;
import io.github.simcards.libcards.network.SocketThread;
import io.github.simcards.libcards.util.GridPosition;

/**
 * Desktop version of SimCards.
 */
public class SimCardsDesktop {

    /** Used to display and update the window. */
    private static Animator animator;
    /** The renderer used to display on the screen. */
    public static GLRenderer renderer;

    /**
     * Initializes a window when the application starts.
     * @param args Unused.
     */
    public static void main(String[] args) {
        renderer = new GLRenderer();

        GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2ES2));
        GLWindow glWindow = GLWindow.create(caps);

        animator = new Animator(glWindow);
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
        animator.start();

        Environment environment = Environment.getEnvironment();

        ZMQ.Context ctx = ZMQ.context(1);
        ZMQ.Socket socket = ctx.socket(ZMQ.PAIR);

        AbsolutelyRankedWar game = new AbsolutelyRankedWar(socket);
        environment.registerTouchHandler(game);

        String addr = "127.0.0.1";

        new Thread(new SocketThread(socket, addr, game)).start();
    }

    /**
     * Stops the animator when the system terminates.
     */
    public static void exit() {
        animator.stop();
    }
}
