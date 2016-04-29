package io.github.simcards.server;

import org.zeromq.ZMQ;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class MatchmakingServer {
    public static void main(String[] args) throws UnknownHostException {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(4000);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        GameServerThread gameServerThread = new GameServerThread("", 2);

        String[] addrs = gameServerThread.getPlayerSocketAddrs();
        for (String s : addrs) { System.out.println(s); }

        gameServerThread.start();

        int i = 0;

        while (true) {
            try {
                Socket sock = serverSocket.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));

                // handle the request
                String gameRequest = reader.readLine();
                writer.write(addrs[i] + "\n");

                // clean up the socket
                reader.close();
                writer.close();
                sock.close();

                i = i % addrs.length;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
