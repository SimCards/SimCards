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


import io.github.simcards.simcards.R;
import io.github.simcards.simcards.client.graphics.GLSurfaceViewWrapper;
import io.github.simcards.simcards.client.graphics.GraphicsUtil;
import io.github.simcards.simcards.client.graphics.ResourceUtil;
import io.github.simcards.simcards.client.network.SocketThread;
import io.github.simcards.simcards.game.AbsolutelyRankedWar;
import io.github.simcards.simcards.game.Environment;

/**
 * Main activity screen.
 */
public class MainActivity extends AppCompatActivity {

    /** Surface for rendering objects. */
    private GLSurfaceView mGLView;

    /** Listens for touch gestures. */
    private GestureDetectorCompat mTouchDetector;
    /** Listens for zoom gestures. */
    private ScaleGestureDetector mZoomDetector;

    private static Context ctx;

    public static Context getContext() {
        if (ctx == null) throw new IllegalStateException();
        return ctx;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ctx = this;

        System.out.println("Application started.");

        ResourceUtil.sResources = this.getResources();

        // Get the Android screen size.
        Display display = getWindowManager().getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);
        GraphicsUtil.screenWidth = screenSize.x;
        GraphicsUtil.screenHeight = screenSize.y;

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        mGLView = new GLSurfaceViewWrapper(this);
        setContentView(mGLView);

        mTouchDetector = new GestureDetectorCompat(this, new TouchListener());
        mZoomDetector = new ScaleGestureDetector(this, new ZoomListener());

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
        mZoomDetector.onTouchEvent(event);
        mTouchDetector.onTouchEvent(event);
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
