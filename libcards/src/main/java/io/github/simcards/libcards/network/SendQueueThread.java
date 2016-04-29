package io.github.simcards.libcards.network;

import org.zeromq.ZMQ;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SendQueueThread {


    private static SendQueueThread thread;

    private boolean keep_going;

    private ZMQ.Socket sock;
    private BlockingQueue<SerializableMsg> queue;


    public static void init(ZMQ.Socket sock) {
        thread = new SendQueueThread(sock);
    }

    public static SendQueueThread getThread() {
        if (thread == null) { throw new IllegalStateException("not yet initialized with socket"); }
        return thread;
    }

    private SendQueueThread(ZMQ.Socket sock) {
        this.sock = sock;
        this.queue = new LinkedBlockingQueue<>();
        this.keep_going = true;
    }

    public void run() {
        while (this.keep_going) {
            try {
                SerializableMsg msg = queue.take();
                sock.send(msg.getBytes());
            } catch (InterruptedException e) {}
        }
        thread = null;
    }

    public void addToQueue(SerializableMsg msg) {
        queue.add(msg);
    }

}
