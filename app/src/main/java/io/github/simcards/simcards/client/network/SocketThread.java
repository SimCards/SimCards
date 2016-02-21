package io.github.simcards.simcards.client.network;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.zeromq.ZMQ;

public class SocketThread implements Runnable {

    private ZMQ.Socket socket;
    private ZMQ.Context ctx;
    private MessageHandler handler;
    private String addr;

    public SocketThread(ZMQ.Socket socket, String addr, MessageHandler handler) {
        this.socket = socket;
        this.addr = addr;
        this.handler = handler;
        this.ctx = ZMQ.context(1);
    }

    @Override
    public void run() {
        // connect and let the server know we are connected
        Log.d("SimCards", "connecting to " + addr);
        socket.connect("tcp://" + addr + ":5001");
        JSONObject connect_msg = new JSONObject();
        try {
            connect_msg.put("type","connected");
            connect_msg.put("user_id","android_phone");
            new ZeroMQAsyncSendTask(socket).execute(connect_msg);
        } catch (JSONException e) {
            Log.e("SimCards", "COULD NOT SEND CONNECT MESSAGE");
            e.printStackTrace();
        }

        // keep responding to received messages
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
