package io.github.simcards.simcards.client.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


import io.github.simcards.libcards.game.GameInfo;

public class GameInfoActivity extends Activity {

    private GameInfo cg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase root = new Firebase("https://flickering-heat-9345.firebaseio.com/web/data");
        root = root.child(getIntent().getExtras().getString("id"));
        Log.e("!", getIntent().getExtras().getString("id"));
        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cg = dataSnapshot.getValue(GameInfo.class);
                Log.e("!!", cg.toString());
                create();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
    private void create() {
        LinearLayout ll = new LinearLayout(this);
        ll.setWeightSum(1.0f);
        ll.setOrientation(LinearLayout.VERTICAL);
        TextView head = new TextView(this);
        head.setText(cg.getName());
        head.setTextSize(25f);
        head.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.1f));
        head.setGravity(Gravity.CENTER);
        ll.addView(head);
        LinearLayout mid = new LinearLayout(this);
        mid.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.4f));
        mid.setWeightSum(1.0f);
        ImageView iv = new ImageView(this);
        byte[] ds = Base64.decode(cg.getImage().substring(cg.getImage().indexOf(",") + 1), Base64.DEFAULT);
        Bitmap db = BitmapFactory.decodeByteArray(ds, 0, ds.length);
        iv.setImageBitmap(db);
        iv.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.5f));
        mid.addView(iv);
        TextView tv = new TextView(this);
        tv.setText(cg.getDesc());
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.5f));
        mid.addView(tv);
        ll.addView(mid);
        TextView stats = new TextView(this);
        stats.setText("random");
        stats.setGravity(Gravity.CENTER);
        stats.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.2f));
        ll.addView(stats);
        TextView rules = new TextView(this);
        rules.setText(cg.getRules());
        rules.setGravity(Gravity.CENTER);
        rules.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.2f));
        ll.addView(rules);
        Button play = new Button(this);
        play.setText("Play");
        play.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.1f));
        ll.addView(play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameInfoActivity.this, MatchmakingActivity.class);
                intent.putExtra(MatchmakingActivity.PARAM_GAME_ID, cg.getID());
                GameInfoActivity.this.startActivity(intent);
            }
        });


        setContentView(ll);
    }

}
