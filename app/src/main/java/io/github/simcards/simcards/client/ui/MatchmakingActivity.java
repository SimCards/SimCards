package io.github.simcards.simcards.client.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.zeromq.ZMQ;

import java.util.Set;

import io.github.simcards.libcards.network.MatchmakingClient;
import io.github.simcards.simcards.R;
import io.github.simcards.simcards.client.util.MakeToast;

public class MatchmakingActivity extends AppCompatActivity {

    public static final String PARAM_GAME_ID = "PARAM_GAME_ID";

    public static final String MM_SERVER_ADDR = "143.215.90.209";

    ProgressBar spinner;
    TextView status;
    private static ZMQ.Socket socket;
    private static Integer playerId;

    private MatchmakingClient mmClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchmaking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get the game identifier parameter
        Intent intent = this.getIntent();
        String gameId = intent.getStringExtra(PARAM_GAME_ID);
        if (gameId == null) {
            gameId = "";
        }


        // initialize UI elements
        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.setVisibility(View.VISIBLE);
        spinner.setIndeterminate(true);

        status = (TextView) findViewById(R.id.matchmaking_textview_status);
        status.setText("Finding Game");

        System.out.println("Starting matchmaking client!");
        // start the matchmaking client
        mmClient = new MatchmakingClient(MM_SERVER_ADDR, gameId, new MyMMListener());
        mmClient.start();
    }

    public class MyMMListener implements MatchmakingClient.MMListener {

        int numPlayersConnected;

        @Override
        public void onConnected(Set<Integer> playersConnected) {
            numPlayersConnected = playersConnected.size();
            MatchmakingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    status.setText("Finding Game\n" + numPlayersConnected + " players found");
                }
            });
        }

        @Override
        public void onSuccess(ZMQ.Socket sock, int player_id) {
            System.out.println("Success!");
            socket = sock;
            playerId = new Integer(player_id);

            Intent intent = new Intent(MatchmakingActivity.this, GameActivity.class);
            MatchmakingActivity.this.startActivity(intent);
        }

        @Override
        public void onFailure(String message) {
            socket = null;
            playerId = null;

            MatchmakingActivity.this.runOnUiThread(new MakeToast(MatchmakingActivity.this, "matchmaking failed: " + message));
            Intent intent = new Intent(MatchmakingActivity.this, MenuActivity.class);

            MatchmakingActivity.this.startActivity(intent);
        }
    }

    public static ZMQ.Socket getSocket() {
        if (socket == null) throw new IllegalStateException("socket not initialized");
        return socket;
    }

    public static int getPlayerId() {
        if (playerId == null) throw new IllegalStateException("playerId not initialized");
        return playerId;
    }


}
