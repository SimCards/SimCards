package io.github.simcards.server;

import org.json.JSONException;
import org.json.JSONObject;
import org.zeromq.ZMQ;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import io.github.simcards.libcards.game.Game;
import io.github.simcards.libcards.network.GameReadyMessage;
import io.github.simcards.libcards.network.MessageType;
import io.github.simcards.libcards.network.SerializableMsg;
import io.github.simcards.libcards.util.RandomUtil;

/**
 * Responsible for getting all of the players connected to the game
 */
public class GameServerConnector {

    private ZMQ.Context ctx;
    private ZMQ.Socket[] socks;
    private String[] addrs;
    private int numPlayers;

    /**
     * Initializes numPlayers sockets for each player to connect to
     * @param numPlayers the number of players that are intended to play this game
     * @throws UnknownHostException
     */
    public GameServerConnector(ZMQ.Context ctx, int numPlayers) throws UnknownHostException {

        String addr = InetAddress.getLocalHost().getHostAddress();
        System.out.println("getLocalHost: " + InetAddress.getLocalHost());

        this.ctx = ctx;
        this.socks = new ZMQ.Socket[numPlayers];
        this.addrs = new String[numPlayers];
        this.numPlayers = numPlayers;


        int beginPort = new Random().nextInt(50000) + 5000;
        System.out.println("begin port: " + beginPort);

        for (int i = 0; i < numPlayers; i++) {
            ZMQ.Socket sock = ctx.socket(ZMQ.PAIR);
            //int port = 6000 + i;
            //sock.bind("tcp://0.0.0.0:" + port);
            int port = sock.bindToRandomPort("tcp://0.0.0.0", beginPort, beginPort + 1000);
            socks[i] = sock;
            addrs[i] = addr + ":" + port;
        }
    }

    /**
     * Returns the addresses of the sockets the clients are expected to connect to
     * @return the addresses of the sockets the clients are expected to connect to
     */
    public String[] getPlayerSockAddrs() {
        return addrs;
    }

    /**
     * Waits until all clients have connected and returns a list of the connected sockets
     * @return a list of the connected sockets
     */
    public ZMQ.Socket[] connectAll() {
        // initialize poller
        ZMQ.Poller poller = new ZMQ.Poller(socks.length);
        for (ZMQ.Socket sock: socks) {
            poller.register(sock, ZMQ.Poller.POLLIN);
        }

        Set<Integer> playersConnected = new HashSet<>();

        System.out.println("waiting for " + numPlayers + " to connect");
        while (playersConnected.size() < numPlayers) {
            // poll until one of our sockets has an event
            poller.poll();

            // check and respond to that event
            for(int i = 0; i < numPlayers; i++) {
                if (poller.pollin(i)) {
                    SerializableMsg msg = SerializableMsg.fromBytes(socks[i].recv());

                    switch (msg.type) {
                        case CONNECTED:
                            if (!playersConnected.contains(i)) {
                                System.out.println("Player " + i + " connected!");
                                // poller.unregister(socks[i]);
                                playersConnected.add(i);
                                updatePlayers(playersConnected);
                            } else {
                                System.out.println("Player " + i + " reconnected for some reason...");
                            }
                            break;
                        default:
                            System.out.println("unhandled msg type: " + msg.type);
                            break;
                    }
                    System.out.println(playersConnected.size() + " of " + numPlayers + " players connected");
                }
            }
        }

        System.out.println("all players connected");
        // send out the game ready message to everyone
        for (int i = 0; i < numPlayers; i++) {
            SerializableMsg msg = new SerializableMsg(MessageType.GAME_READY, new GameReadyMessage(i, numPlayers));
            socks[i].send(msg.getBytes());
        }

        return this.socks;
    }

    private void updatePlayers(Set<Integer> playersConnected) {
        // format a message with all players that have connected
        SerializableMsg update = new SerializableMsg(MessageType.CONNECT_UPDATE, new ArrayList(playersConnected));
        // send out the message to all connected players
        for (Integer player_id : playersConnected) {
            socks[player_id].send(update.getBytes());
        }
    }

    private void sendAll(SerializableMsg msg) {
        for (int i = 0; i < socks.length; i++) {
            socks[i].send(msg.getBytes());
        }
    }
}
