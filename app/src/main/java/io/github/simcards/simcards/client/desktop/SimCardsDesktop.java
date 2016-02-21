package io.github.simcards.simcards.client.desktop;


import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.Animator;

import java.util.ArrayList;
import java.util.List;

import io.github.simcards.simcards.client.graphics.GLRenderer;
import io.github.simcards.simcards.client.graphics.GraphicsUtil;
import io.github.simcards.simcards.game.Card;
import io.github.simcards.simcards.game.Deck;
import io.github.simcards.simcards.game.Environment;
import io.github.simcards.simcards.game.Rank;
import io.github.simcards.simcards.game.Suit;
import io.github.simcards.simcards.game.Visibility;
import io.github.simcards.simcards.util.GridPosition;

/**
 * Desktop version of SimCards.
 */
public class SimCardsDesktop {

    /** Used to display and update the window. */
    private static Animator sAnimator;
    /** The renderer used to display on the screen. */
    public static GLRenderer sRenderer;

    /**
     * Initializes a window when the application starts.
     * @param args Unused.
     */
    public static void main(String[] args) {
        sRenderer = new GLRenderer();

        GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2ES2));
        GLWindow glWindow = GLWindow.create(caps);

        sAnimator = new Animator(glWindow);
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
        sAnimator.start();

        Environment environment = Environment.getEnvironment();
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.ACE, Suit.SPADE));
        Deck deck = new Deck(cards, new GridPosition(), Visibility.FACE_DOWN);
        environment.addNewDeck(deck);

        List<Card> cards2 = new ArrayList<>();
        cards2.add(new Card(Rank.ACE, Suit.HEART));
        Deck deck2 = new Deck(cards2, new GridPosition(1, 0), Visibility.FACE_UP);
        environment.addNewDeck(deck2);

        List<Card> cards3 = new ArrayList<>();
        cards3.add(new Card(Rank.ACE, Suit.CLUB));
        Deck deck3 = new Deck(cards3, new GridPosition(-1, 0), Visibility.FACE_UP);
        environment.addNewDeck(deck3);

        List<Card> cards4 = new ArrayList<>();
        cards4.add(new Card(Rank.ACE, Suit.DIAMOND));
        Deck deck4 = new Deck(cards4, new GridPosition(0, 1), Visibility.FACE_UP);
        environment.addNewDeck(deck4);
    }

    /**
     * Stops the animator when the system terminates.
     */
    public static void exit() {
        sAnimator.stop();
    }
}
