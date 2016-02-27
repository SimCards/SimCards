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

import org.zeromq.ZMQ;


import io.github.simcards.libcards.util.Factory;
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

/**
 * Main activity screen.
 */
public class MainActivity extends AppCompatActivity {

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

        ZMQ.Context ctx = ZMQ.context(1);
        ZMQ.Socket socket = ctx.socket(ZMQ.PAIR);

        AbsolutelyRankedWar game = new AbsolutelyRankedWar(socket);
        environment.registerTouchHandler(game);

        Intent intent = getIntent();
        String addr = intent.getStringExtra(MatchmakingActivity.PARAM_IP_ADDRESS);

        new Thread(new SocketThread(socket, addr, game)).start();

//        List<Card> cards = new ArrayList<>();
//        cards.add(new Card(Rank.ACE, Suit.SPADE));
//        Deck deck = new Deck(cards, new GridPosition(), Visibility.FACE_DOWN);
//        environment.addNewDeck(deck);
//
//        List<Card> cards2 = new ArrayList<>();
//        cards2.add(new Card(Rank.ACE, Suit.HEART));
//        Deck deck2 = new Deck(cards2, new GridPosition(1, 0), Visibility.FACE_UP);
//        environment.addNewDeck(deck2);
//
//        List<Card> cards3 = new ArrayList<>();
//        cards3.add(new Card(Rank.ACE, Suit.CLUB));
//        Deck deck3 = new Deck(cards3, new GridPosition(-1, 0), Visibility.FACE_UP);
//        environment.addNewDeck(deck3);
//
//        List<Card> cards4 = new ArrayList<>();
//        cards4.add(new Card(Rank.ACE, Suit.DIAMOND));
//        Deck deck4 = new Deck(cards4, new GridPosition(0, 1), Visibility.FACE_UP);
//        environment.addNewDeck(deck4);


//        System.out.println("advanceState1");
//        game.advanceState(1);
//
//        try { Thread.sleep(1000); } catch (InterruptedException e) {}
//
//        System.out.println("advanceState0");
//        game.advanceState(0);
//
        // advanced war simulation

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
