package io.github.simcards.simcards.client.ui;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import java.util.ArrayList;
import java.util.List;

import io.github.simcards.simcards.R;
import io.github.simcards.simcards.client.graphics.GLSurfaceViewWrapper;
import io.github.simcards.simcards.client.graphics.GraphicsUtil;
import io.github.simcards.simcards.game.Card;
import io.github.simcards.simcards.game.Deck;
import io.github.simcards.simcards.game.Environment;
import io.github.simcards.simcards.game.Rank;
import io.github.simcards.simcards.game.Suit;
import io.github.simcards.simcards.game.Visibility;
import io.github.simcards.simcards.util.GridPosition;

/**
 * Main activity screen.
 */
public class MainActivity extends AppCompatActivity {

    /** Surface for rendering objects. */
    private GLSurfaceView mGLView;

    /** Listens for pan gestures. */
    private GestureDetectorCompat mPanDetector;
    /** Listens for zoom gestures. */
    private ScaleGestureDetector mZoomDetector;

    /** The field on which the card game is played. */
    private Environment mEnvironment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("Application started.");

        GraphicsUtil.sResources = this.getResources();

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        mGLView = new GLSurfaceViewWrapper(this);
        setContentView(mGLView);

        mPanDetector = new GestureDetectorCompat(this, new PanListener());
        mZoomDetector = new ScaleGestureDetector(this, new ZoomListener());

        mEnvironment = new Environment();

        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.ACE, Suit.SPADE));
        Deck deck = new Deck(cards, new GridPosition(), Visibility.FACE_DOWN);
        mEnvironment.addNewDeck(deck);

        List<Card> cards2 = new ArrayList<>();
        cards2.add(new Card(Rank.ACE, Suit.HEART));
        Deck deck2 = new Deck(cards2, new GridPosition(1, 0), Visibility.FACE_UP);
        mEnvironment.addNewDeck(deck2);

        List<Card> cards3 = new ArrayList<>();
        cards3.add(new Card(Rank.ACE, Suit.CLUB));
        Deck deck3 = new Deck(cards3, new GridPosition(-1, 0), Visibility.FACE_UP);
        mEnvironment.addNewDeck(deck3);

        List<Card> cards4 = new ArrayList<>();
        cards4.add(new Card(Rank.ACE, Suit.DIAMOND));
        Deck deck4 = new Deck(cards4, new GridPosition(0, 1), Visibility.FACE_UP);
        mEnvironment.addNewDeck(deck4);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mZoomDetector.onTouchEvent(event);
        mPanDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
