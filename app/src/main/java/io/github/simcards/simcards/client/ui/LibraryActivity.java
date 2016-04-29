package io.github.simcards.simcards.client.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import io.github.simcards.libcards.game.GameInfo;
import io.github.simcards.simcards.R;

public class LibraryActivity extends Activity {
    protected ArrayList<GameInfo> all;
    private int size = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("T", "trying here");
        Firebase.setAndroidContext(this);
        all = new ArrayList<GameInfo>();
        Firebase root = new Firebase("https://flickering-heat-9345.firebaseio.com/web/data");
//        root.child("1").setValue(new GameInfo("Za","bb",1));
//        root.child("2").setValue(new GameInfo("Yc","dd",2));
//        root.child("3").setValue(new GameInfo("Xe", "ff", 3));
//        root.child("4").setValue(new GameInfo("Ag", "hh", 4));
//        root.child("5").setValue(new GameInfo("eAg", "hh", 4));
        Query qref = root.orderByChild("name").limitToLast(size);
        qref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> ds = dataSnapshot.getChildren().iterator();
                while (ds.hasNext()) {
                    GameInfo cg = ds.next().getValue(GameInfo.class);
                    all.add(cg);
                    Log.e("!", cg.getName() + "");
                }
                drawBackground();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        //setContentView(R.layout.activity_library);
    }
    private void drawBackground() {

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setWeightSum(1.0f);

        LinearLayout uh = new LinearLayout(this);
        uh.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.5f));
        uh.setWeightSum(1.0f);
        uh.setOrientation(LinearLayout.VERTICAL);
        TextView feat = new TextView(this);
        feat.setText("Featured");
        feat.setGravity(Gravity.CENTER);
        feat.setTextColor(Color.BLACK);
        feat.setTextSize(25f);
        feat.setBackgroundResource(R.drawable.layout_bg);
        feat.setTypeface(null, Typeface.BOLD);
        feat.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.2f));
        uh.addView(feat);

        LinearLayout fh = new LinearLayout(this);
        fh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("!!", "clicked");
                int index = ((LinearLayout)v.getParent()).indexOfChild(v);
                Log.e("!!", index+"");
                Intent intent = new Intent(v.getContext(), GameInfoActivity.class);
                intent.putExtra("id", all.get(0).getID());
                //intent.putExtra("GameInfo", all.get(index+1));
                startActivity(intent);
            }
        });
        fh.setBackgroundResource(R.drawable.layout_bg);
        fh.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.8f));
        fh.setWeightSum(1.0f);
        ImageView iv = new ImageView(this);
        iv.setPadding(10, 10, 10, 10);
        iv.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.5f));
        byte[] ds = Base64.decode(all.get(0).getImage().substring(all.get(0).getImage().indexOf(",")+1), Base64.DEFAULT);
        Bitmap db = BitmapFactory.decodeByteArray(ds, 0, ds.length);
        iv.setImageBitmap(db);
        fh.addView(iv);

        LinearLayout tv = new LinearLayout(this);
        tv.setOrientation(LinearLayout.VERTICAL);
        tv.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.5f));
        tv.setWeightSum(1.0f);
        TextView t1 = new TextView(this);
        t1.setText(all.get(0).getName());
        t1.setTypeface(null, Typeface.BOLD);
        t1.setGravity(Gravity.CENTER);
        t1.setTextColor(Color.BLACK);
        t1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.2f));
        tv.addView(t1);
        TextView t2 = new TextView(this);
        t2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.8f));
        tv.addView(t2);
        fh.addView(tv);

        uh.addView(fh);

        ll.addView(uh);

        LinearLayout lh = new LinearLayout(this);
        lh.setOrientation(LinearLayout.VERTICAL);
        lh.setWeightSum(1.0f);
        lh.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.5f));
        float weight = 0.8f/(size-1);
        for (int x = 1; x < size; x++) {
            LinearLayout temp = new LinearLayout(this);
            temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("!!", "clicked");
                    int index = ((LinearLayout)v.getParent()).indexOfChild(v);
                    Log.e("!!", index+"");
                    Intent intent = new Intent(v.getContext(), GameInfoActivity.class);
                    intent.putExtra("id", all.get(index + 1).getID());
                    //intent.putExtra("GameInfo", all.get(index+1));
                    startActivity(intent);
                }
            });
            temp.setBackgroundResource(R.drawable.layout_bg);
            temp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, weight));
            temp.setWeightSum(1.0f);
            ImageView v = new ImageView(this);
            v.setPadding(10,10,10,10);
            byte[] decodedString = Base64.decode(all.get(x).getImage().substring(all.get(x).getImage().indexOf(",")+1), Base64.DEFAULT);
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
            lh.addView(temp);
        }
        Button search = new Button(this);
        search.setText("Search");
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SearchActivity    .class);
                startActivity(intent);
            }
        });
        search.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.2f));
        lh.addView(search);

        ll.addView(lh);
        setContentView(ll);
    }
}
