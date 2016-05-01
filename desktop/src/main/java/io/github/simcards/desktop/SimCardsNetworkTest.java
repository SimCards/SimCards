package io.github.simcards.desktop;

import org.zeromq.ZMQ;

import io.github.simcards.libcards.network.MessageType;
import io.github.simcards.libcards.network.SerializableMsg;

/**
 * Created by Vishal on 4/30/16.
 */
public class SimCardsNetworkTest {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                System.out.println("Shutdown hook ran!");
                ZMQ.context(1).term();
            }
        });

        System.out.println("Initializing");
        ZMQ.Context ctx = ZMQ.context(1);
        ZMQ.Socket sock = ctx.socket(ZMQ.PAIR);

        String addr = "tcp://143.215.90.209:49155";

        System.out.println("connecting to " + addr);
        sock.connect(addr);

        System.out.println("Sending...");
        sock.send(new SerializableMsg(MessageType.CONNECTED, "bs player").getBytes());

        while (true) {
            System.out.println("Receiving...");
            SerializableMsg msg = SerializableMsg.fromBytes(sock.recv());

            System.out.println(msg.type);
            System.out.println(msg.getContent());
        }

    }
}
