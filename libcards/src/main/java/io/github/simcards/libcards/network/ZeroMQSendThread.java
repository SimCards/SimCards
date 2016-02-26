package io.github.simcards.libcards.network;

import org.json.JSONObject;
import org.zeromq.ZMQ;

public class ZeroMQSendThread extends Thread {

    private ZMQ.Socket socket;
    private JSONObject json_msg;

    public ZeroMQSendThread(ZMQ.Socket socket, JSONObject msg) {
        this.socket = socket;
        this.json_msg = msg;
    }

    @Override
    public void run() {
        System.out.println("Sending message on new thread");
        String msg = json_msg.toString();
        System.out.println("Sending out message");
        System.out.println(msg);
        socket.send(msg);
        System.out.println("Sent message and exiting thread");
    }
}