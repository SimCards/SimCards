package io.github.simcards.libcards.network;

import org.zeromq.ZMQ;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class SendQueueThread extends Thread {

    /** Lock for environment updating and serializing. */
    public static Object messageLock = new Object();

    private static SendQueueThread thread;

    private boolean keep_going;

    private ZMQ.Socket[] socks;
    private BlockingQueue<SerializableMsg> queue;

    public static void init(ZMQ.Socket[] socks) {
        thread = new SendQueueThread(socks);
        thread.start();
    }

    public static SendQueueThread getThread() {
        if (thread == null) { throw new IllegalStateException("not yet initialized with socket"); }
        return thread;
    }

    public SendQueueThread(ZMQ.Socket[] socks) {
        this.socks = socks;
        this.queue = new LinkedBlockingQueue<>();
        this.keep_going = true;
    }

    @Override
    public void run() {
        while (this.keep_going || this.queue.size() > 0) {
            try {
                SerializableMsg msg = queue.poll(500, TimeUnit.MILLISECONDS);
                if (msg != null) {
                    synchronized (messageLock) {
                        System.out.println("Sending out msg of type " + msg.type);
                        for (int i = 0; i < socks.length; i++) {
                            socks[i].send(msg.getBytes());
                        }
                    }
                }
            } catch (InterruptedException e) {}
        }
        thread = null;
    }

    public void end() {
        this.keep_going = false;
    }

    public void addToQueue(SerializableMsg msg) {
        System.out.println("adding " + msg.type + " to queue");
        queue.add(msg);
    }

}
