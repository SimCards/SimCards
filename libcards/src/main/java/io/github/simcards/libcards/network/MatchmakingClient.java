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
        try {
            // initialize socket
            sock = new Socket(host, 4000);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));

            // get our socket for the game server
            writer.write(gameId + "\n");
            gameServerAddress = reader.readLine();

            // clean up the socket
            writer.close();
            reader.close();
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
            listener.onFailure(e.getMessage());
            return;
        }

        // join the game server

        ZMQ.Context ctx = ZMQ.context(1);
        ZMQ.Socket gameSock = ctx.socket(ZMQ.PAIR);
        gameSock.connect(gameServerAddress);

        SerializableMsg connectMsg = new SerializableMsg(MessageType.CONNECTED, "player_name");
        gameSock.send(connectMsg.getBytes());

        GameReadyMessage grm = null;
        boolean gameReady = false;
        while (!gameReady) {
            SerializableMsg recvMsg = SerializableMsg.fromBytes(gameSock.recv());

            switch (recvMsg.type) {
                case CONNECT_UPDATE:
                    Set<Integer> playersConnected = new HashSet<>((ArrayList<Integer>) recvMsg.getContent());
                    listener.onConnected(playersConnected);
                    break;
                case GAME_READY:
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
