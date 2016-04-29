package io.github.simcards.simcards.client.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import io.github.simcards.libcards.game.GameInfo;
import io.github.simcards.simcards.R;


public class SearchActivity extends Activity {

    LinearLayout results;
    EditText search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout ll = new LinearLayout(this);
        ll.setWeightSum(1.0f);
        ll.setOrientation(LinearLayout.VERTICAL);
        LinearLayout sea = new LinearLayout(this);
        sea.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0 , 0.1f));
        sea.setWeightSum(1.0f);
        search = new EditText(this);
        search.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.5f));
        sea.addView(search);
        Button sear = new Button(this);
        sear.setText("Search");
        sear.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.5f));
        sear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSearch();
            }
        });
        sea.addView(sear);
        ll.addView(sea);
        ScrollView sv = new ScrollView(this);
        sv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.9f));
        results = new LinearLayout(this);
        sv.addView(results);
        ll.addView(sv);
        setContentView(ll);
    }
    ArrayList<GameInfo> all;
    String query;
    private void doSearch() {
        query = search.getText().toString();
        Firebase root = new Firebase("https://flickering-heat-9345.firebaseio.com/web/data");
        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> ds = dataSnapshot.getChildren().iterator();
                all = new ArrayList<GameInfo>();
                while (ds.hasNext()) {
                    GameInfo cg = ds.next().getValue(GameInfo.class);
                    if (cg.getName().contains(query)) {
                        all.add(cg);
                        Log.e("!", "ADDED");
                    }
                    Log.e("!", cg.getName() + "");
                }
                updateResults();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
    private void updateResults() {
        results.removeAllViews();
        results.setOrientation(LinearLayout.VERTICAL);
        results.setWeightSum(1.0f);
        for (int x = 0; x < all.size(); x++) {
            LinearLayout temp = new LinearLayout(this);
            temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("!!", "clicked");
                    int index = ((LinearLayout)v.getParent()).indexOfChild(v);
                    Log.e("!!", index+"");
                    Intent intent = new Intent(v.getContext(), GameInfoActivity.class);
                    intent.putExtra("id", all.get(index).getID());
                    //intent.putExtra("cardgame", all.get(index+1));
                    startActivity(intent);
                }
            });
            temp.setBackgroundResource(R.drawable.layout_bg);
            temp.setWeightSum(1.0f);
            temp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 500));
            ImageView v = new ImageView(this);
            v.setPadding(10,10,10,10);
            byte[] decodedString = Base64.decode(all.get(x).getImage().substring(all.get(x).getImage().indexOf(",") + 1), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            v.setImageBitmap(decodedByte);
            v.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.3f));
            temp.addView(v);
            TextView t = new TextView(this);
            t.setText(all.get(x).getName());
            t.setTextColor(Color.BLACK);
            t.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.7f));
            t.setGravity(Gravity.CENTER);
            temp.addView(t);
            results.addView(temp);
        }
    }
}
