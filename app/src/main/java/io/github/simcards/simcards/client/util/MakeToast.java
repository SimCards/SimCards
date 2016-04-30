package io.github.simcards.simcards.client.util;

import android.content.Context;
import android.widget.Toast;

public class MakeToast implements Runnable {

    private String message;
    private Context ctx;

    public MakeToast(Context ctx, String message) {
        this.ctx = ctx;
        this.message = message;
    }

    @Override
    public void run() {
        Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
    }
}
