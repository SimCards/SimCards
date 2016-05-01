package io.github.simcards.libcards.network;

import org.json.JSONObject;
import org.zeromq.ZMQ;

import java.io.Serializable;

public class ZeroMQSendThread extends Thread {

    private ZMQ.Socket socket;
    private SerializableMsg msg;

    public ZeroMQSendThread(ZMQ.Socket socket, SerializableMsg msg) {
        this.socket = socket;
        this.msg = msg;
    }

    @Override
    public void run() {
        System.out.println("Sending message on new thread");
        socket.send(msg.getBytes());
        System.out.println("Sent message and exiting thread");
    }
}