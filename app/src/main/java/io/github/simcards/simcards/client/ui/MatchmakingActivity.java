package io.github.simcards.simcards.client.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.github.simcards.simcards.R;

public class MatchmakingActivity extends AppCompatActivity {

    public static final String PARAM_IP_ADDRESS = "PARAM_IP_ADDRESS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchmaking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button find_match = (Button) findViewById(R.id.button_find_match);
        find_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText server_ip = (EditText) MatchmakingActivity.this.findViewById(R.id.editText_server_ip);
                String addr = server_ip.getText().toString();
                Log.d("SimCards", addr);
                Intent intent = new Intent(MatchmakingActivity.this, MainActivity.class);
                intent.putExtra(PARAM_IP_ADDRESS, addr);
                startActivity(intent);
            }
        });

    }



}
