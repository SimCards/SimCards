package io.github.simcards.simcards.client.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.wifi.WifiManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.Toast;

import org.zeromq.ZMQ;


import java.util.ArrayList;
import java.util.List;

import io.github.simcards.libcards.game.Card;
import io.github.simcards.libcards.game.CardGameEvent;
import io.github.simcards.libcards.game.Deck;
import io.github.simcards.libcards.game.Game;
import io.github.simcards.libcards.game.Player;
import io.github.simcards.libcards.network.GameClient;
import io.github.simcards.libcards.util.Factory;
import io.github.simcards.libcards.util.TouchHandler;
import io.github.simcards.simcards.R;
import io.github.simcards.simcards.client.graphics.AndroidGLWrapper;
import io.github.simcards.simcards.client.graphics.GLSurfaceViewWrapper;
import io.github.simcards.libcards.graphics.GraphicsUtil;
import io.github.simcards.simcards.client.graphics.ResourceUtil;
import io.github.simcards.libcards.network.SocketThread;
import io.github.simcards.libcards.game.AbsolutelyRankedWar;
import io.github.simcards.libcards.game.Environment;
import io.github.simcards.simcards.client.util.AndroidLogger;
import io.github.simcards.simcards.client.util.AndroidMiddleman;
import io.github.simcards.simcards.client.util.MakeToast;

/**
 * Main activity screen.
 */
public class GameActivity extends AppCompatActivity {

    /** Surface for rendering objects. */
    private GLSurfaceView glView;

    /** Listens for touch gestures. */
    private GestureDetectorCompat touchDetector;
    /** Listens for zoom gestures. */
    private ScaleGestureDetector zoomDetector;

    /** The context that the activity is in. */
    private static Context ctx;

    /**
     * Gets the context that the activity is in.
     * @return The context that the activity is in.
     */
    public static Context getContext() {
        if (ctx == null) throw new IllegalStateException();
        return ctx;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        System.out.println("Application started.");
        Factory.init(new AndroidLogger(), new AndroidGLWrapper(), null, new AndroidMiddleman());
        ResourceUtil.resources = this.getResources();

        // Get the Android screen size.
        Display display = getWindowManager().getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);
        GraphicsUtil.screenWidth = screenSize.x;
        GraphicsUtil.screenHeight = screenSize.y;

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        GLSurfaceViewWrapper glSurfaceViewWrapper = new GLSurfaceViewWrapper(this);
        glView = glSurfaceViewWrapper;
        Factory.setRerenderer(glSurfaceViewWrapper);
        setContentView(glView);

        touchDetector = new GestureDetectorCompat(this, new TouchListener());
        zoomDetector = new ScaleGestureDetector(this, new ZoomListener());

        Environment environment = Environment.getEnvironment();

        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        System.out.println("Using ip address " + ip);

        final GameClient gameClient = new GameClient(MatchmakingActivity.getSocket(), new GameClient.GameClientListener() {
            @Override
            public void onGameOver() {
                GameActivity.this.runOnUiThread(new MakeToast(GameActivity.this, "GAME OVER"));
                Intent intent = new Intent(GameActivity.this, MenuActivity.class);
                GameActivity.this.startActivity(intent);
            }
        });
        environment.registerTouchHandler(new TouchHandler() {
            @Override
            public void handleTouch(Deck deck, Card card) {
                // actually figure out the player's id
                Player player = new Player(MatchmakingActivity.getPlayerId());
                List<Deck> decks = new ArrayList<>(1);
                decks.add(deck);
                List<List<Card>> cards = new ArrayList<>(1);
                List<Card> nestedCards = new ArrayList<>(1);
                nestedCards.add(card);
                cards.add(nestedCards);
                CardGameEvent event = new CardGameEvent(player, decks, cards);
                gameClient.handleInput(event);
            }
        });

        new Thread(gameClient).start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        zoomDetector.onTouchEvent(event);
        touchDetector.onTouchEvent(event);
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
