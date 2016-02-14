package io.github.simcards.simcards.client.network;



import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.zeromq.ZMQ;

public class SocketThread implements Runnable {

    ZMQ.Socket socket;
    ZMQ.Context ctx;
    MessageHandler handler;

    public SocketThread(ZMQ.Socket socket, MessageHandler handler) {
        this.socket = socket;
        this.handler = handler;
        this.ctx = ZMQ.context(1);
    }

    @Override
    public void run() {





        socket.connect("tcp://143.215.94.247:5001");


        JSONObject connect_msg = new JSONObject();
        try {
            connect_msg.put("type","connected");
            connect_msg.put("user_id","android_phone");
            new ZeroMQAsyncSendTask(socket).execute(connect_msg);
        } catch (JSONException e) {
            Log.e("SimCards", "COULD NOT SEND CONNECT MESSAGE");
            e.printStackTrace();
        }


        while(!Thread.currentThread().isInterrupted()) {
            String str_msg = socket.recvStr();
            System.out.println("Received message!");
            try {
                JSONObject msg = new JSONObject(str_msg);
                handler.handleMessage(msg);
            } catch (JSONException e) {
                Log.e("SimCards", e.toString());
            }
        }
        socket.close();
        ctx.term();
    }
}
