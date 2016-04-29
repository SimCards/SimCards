package io.github.simcards.simcards.client.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.zeromq.ZMQ;

import java.util.Set;

import io.github.simcards.libcards.game.AbsolutelyRankedWarGame;
import io.github.simcards.libcards.game.Game;
import io.github.simcards.libcards.game.GameInfo;
import io.github.simcards.libcards.network.MatchmakingClient;
import io.github.simcards.simcards.R;

public class MatchmakingActivity extends AppCompatActivity {

    public static final String PARAM_IP_ADDRESS = "PARAM_IP_ADDRESS";

    ProgressBar spinner;
    TextView status;
    private static ZMQ.Socket socket;
    private static Integer playerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchmaking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.setVisibility(View.VISIBLE);
        spinner.setIndeterminate(true);

        status = (TextView) findViewById(R.id.matchmaking_textview_status);
        status.setText("Finding Game");
    }

    public class MyMMListener implements MatchmakingClient.MMListener {

        @Override
        public void onConnected(Set<Integer> playersConnected) {
            status.setText("Finding Game\n" + playersConnected.size() + " players found");
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
        public void onFailure() {
            socket = null;
            playerId = null;
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
