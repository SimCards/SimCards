package io.github.simcards.simcards.client.util;

import android.util.Log;
import android.widget.Toast;

import io.github.simcards.libcards.util.ILog;
import io.github.simcards.simcards.client.ui.GameActivity;

public class AndroidLogger implements ILog {
    public void o(Object o) {
        Log.i("SimCards", o.toString());
    }

    public void e(Object o) {
        this.o(o);
    }

    public void d(Object o) {
        this.o(o);
    }

    public void i(Object o) {
        this.o(o);
    }

    public void notice(Object o) {
        Toast.makeText(GameActivity.getContext(), o.toString(), Toast.LENGTH_LONG);
    }
}
