package io.github.simcards.simcards.client.network;

import android.os.AsyncTask;
import android.os.Handler;

import org.json.JSONObject;
import org.zeromq.ZMQ;

public class ZeroMQAsyncSendTask extends AsyncTask<JSONObject, Void, Void> {

    private ZMQ.Socket socket;

    public ZeroMQAsyncSendTask(ZMQ.Socket socket) {
        this.socket = socket;
    }

    @Override
    protected Void doInBackground(JSONObject... params) {
        String msg = params[0].toString();
        System.out.println("Sending out message");
        System.out.println(msg);
        socket.send(msg);
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {}
}