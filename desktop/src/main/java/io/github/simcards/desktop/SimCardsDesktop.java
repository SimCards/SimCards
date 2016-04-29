package io.github.simcards.desktop;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.Animator;

import org.zeromq.ZMQ;

import java.util.ArrayList;
import java.util.List;

import io.github.simcards.libcards.game.AbsolutelyRankedWar;
import io.github.simcards.libcards.game.enums.Arrangement;
import io.github.simcards.libcards.game.enums.Facing;
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
        initializeTestEnvironment();

        ZMQ.Context ctx = ZMQ.context(1);
        ZMQ.Socket socket = ctx.socket(ZMQ.PAIR);

        AbsolutelyRankedWar game = new AbsolutelyRankedWar(socket);
        environment.registerTouchHandler(game);

        String addr = "127.0.0.1";

        new Thread(new SocketThread(socket, addr, game)).start();
    }

    /**
     * Initializes a test play field for UI testing.
     */
    private static void initializeTestEnvironment() {
        Environment environment = Environment.getEnvironment();
        List<Card> cards = new ArrayList<>();
        for (int i = 0 ; i < 52; i++) {
            cards.add(new Card(Rank.ACE, Suit.SPADE));
        }
        Deck deck = new Deck(cards, new Visibility(Facing.FACE_DOWN, true, Arrangement.STACKED));
        environment.addNewDeck(deck, new GridPosition(), 45);

        List<Card> cards2 = new ArrayList<>();
        cards2.add(new Card(Rank.ACE, Suit.HEART));
        cards2.add(new Card(Rank.TWO, Suit.HEART));
        cards2.add(new Card(Rank.THREE, Suit.HEART));
        Deck deck2 = new Deck(cards2, new Visibility(Facing.TOP_FACE_UP, false, Arrangement.HORIZONTAL));
        environment.addNewDeck(deck2, new GridPosition(1, 0), 15);

        List<Card> cards3 = new ArrayList<>();
        cards3.add(new Card(Rank.ACE, Suit.CLUB));
        cards3.add(new Card(Rank.TWO, Suit.CLUB));
        cards3.add(new Card(Rank.THREE, Suit.CLUB));
        Deck deck3 = new Deck(cards3, new Visibility(Facing.FACE_UP, false, Arrangement.VERTICAL));
        environment.addNewDeck(deck3, new GridPosition(-1, 0), 345);

        List<Card> cards4 = new ArrayList<>();
        cards4.add(new Card(Rank.ACE, Suit.DIAMOND));
        Deck deck4 = new Deck(cards4, new Visibility(Facing.FACE_UP));
        environment.addNewDeck(deck4, new GridPosition(0, 1));

        List<Card> cards5 = new ArrayList<>();
        cards5.add(new Card(Rank.KING, Suit.SPADE));
        cards5.add(new Card(Rank.QUEEN, Suit.SPADE));
        cards5.add(new Card(Rank.JACK, Suit.SPADE));
        cards5.add(new Card(Rank.TEN, Suit.SPADE));
        cards5.add(new Card(Rank.NINE, Suit.SPADE));
        cards5.add(new Card(Rank.EIGHT, Suit.SPADE));
        cards5.add(new Card(Rank.SEVEN, Suit.SPADE));
        cards5.add(new Card(Rank.SIX, Suit.SPADE));
        cards5.add(new Card(Rank.FIVE, Suit.SPADE));
        cards5.add(new Card(Rank.FOUR, Suit.SPADE));
        cards5.add(new Card(Rank.THREE, Suit.SPADE));
        cards5.add(new Card(Rank.TWO, Suit.SPADE));
        cards5.add(new Card(Rank.ACE, Suit.SPADE));
        Deck deck5 = new Deck(cards5, 0);
        environment.addHand(deck5);
    }

    /**
     * Stops the animator when the system terminates.
     */
    public static void exit() {
        animator.stop();
    }
}
