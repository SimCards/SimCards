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

import io.github.simcards.libcards.util.RandomUtil;

public class MatchmakingClient {

    private String host;
    private int gameIdentifier;
    private int playerName;
    private MMListener listener;

    public MatchmakingClient(String host, MMListener listener) {
        this.host = host;
        this.playerName = 0;
        this.gameIdentifier = 0;
        this.listener = listener;
    }

    public void run() throws UnknownHostException, IOException {
        Socket sock = new Socket(host, 4000);

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));

        writer.write("gameIdentifier: " + gameIdentifier + "\n");
        String gameServerAddress = reader.readLine();

        writer.close();
        reader.close();
        sock.close();

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
        void onFailure();
    }
}
