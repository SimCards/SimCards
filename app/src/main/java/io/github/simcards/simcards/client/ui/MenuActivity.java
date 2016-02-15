package io.github.simcards.simcards.client.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import io.github.simcards.simcards.R;

public class MenuActivity extends AppCompatActivity {


    private static Context ctx;

    private static Context getContext() {
        return ctx;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ctx = this;

        Button play = (Button) findViewById(R.id.button_play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MenuActivity.getContext(), "play functionality coming soon!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MenuActivity.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        Button create = (Button) findViewById(R.id.button_create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MenuActivity.getContext(), "create functionality coming soon!", Toast.LENGTH_SHORT).show();
            }
        });

        Button share = (Button) findViewById(R.id.button_share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MenuActivity.getContext(), "share functionality coming soon!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
