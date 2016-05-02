package io.github.simcards.libcards.network;

import org.json.JSONArray;
import org.json.JSONObject;
import org.zeromq.ZMQ;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import io.github.simcards.libcards.graphics.GameScreen;

public class MatchmakingClient extends Thread {

    private String host;
    private String gameId;
    private int playerName;
    private MMListener listener;

    public MatchmakingClient(String host, String gameId, MMListener listener) {
        this.host = host;
        this.playerName = 0;
        this.gameId = gameId;
        this.listener = listener;
    }

    @Override
    public void run() {
        Socket sock;
        String gameServerAddress;
        int mm_server_port = 4000;
        try {
            System.out.println("Initializing socket to " + host + " on port " + mm_server_port);
            // initialize socket
            sock = new Socket(host, mm_server_port);
            System.out.println("Initialized socket");
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));

            // get our socket for the game server
            System.out.println("Writing game request: " + gameId);
            writer.write(gameId + "\n");
            writer.flush();
            System.out.println("Waiting for game address");
            gameServerAddress = reader.readLine();
            if (gameServerAddress == null) {
                System.out.println("no address received");
                listener.onFailure("server unavailable");
                return;
            }
            System.out.println("Received gameServerAddress: " + gameServerAddress);

            // clean up the socket
            writer.close();
            reader.close();
            sock.close();
        } catch (IOException e) {
            System.out.println("Something went wrong with the matchmaking client");
            e.printStackTrace();
            listener.onFailure(e.getMessage());
            return;
        }

        // join the game server
        System.out.println("initializing zmq game sock");
        ZMQ.Context ctx = ZMQ.context(1);
        ZMQ.Socket gameSock = ctx.socket(ZMQ.PAIR);
        System.out.println("trying to connect game sock to tcp://" + gameServerAddress);
        gameSock.connect("tcp://" + gameServerAddress);

        System.out.println("Sending connected message");
        SerializableMsg connectMsg = new SerializableMsg(MessageType.CONNECTED, "player_name");
        gameSock.send(connectMsg.getBytes());

        System.out.println("waiting to receive game ready message");
        GameReadyMessage grm = null;
        boolean gameReady = false;
        while (!gameReady) {
            SerializableMsg recvMsg = SerializableMsg.fromBytes(gameSock.recv());

            switch (recvMsg.type) {
                case CONNECT_UPDATE:
                    System.out.println("Received connect update");
                    Set<Integer> playersConnected = new HashSet<>((ArrayList<Integer>) recvMsg.getContent());
                    GameScreen.getScreen().setPlayerID(playersConnected.size() - 1);
                    listener.onConnected(playersConnected);
                    break;
                case GAME_READY:
                    System.out.println("Received game ready message");
                    gameReady = true;
                    grm = (GameReadyMessage) recvMsg.getContent();
                    break;
                default:
                    System.out.println("unhandled msg type: " + recvMsg.type.toString());
            }
        }

        listener.onSuccess(gameSock, grm.playerID);
    }

    public interface MMListener {
        void onConnected(Set<Integer> playersConnected);
        void onSuccess(ZMQ.Socket sock, int player_id);
        void onFailure(String message);
    }
}
