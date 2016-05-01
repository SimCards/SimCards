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
                System.out.println("Accepting mm requests...(index: " + i + ")");
                Socket sock = serverSocket.accept();
                System.out.println("Accepted mm request");
                BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));

                // handle the request
                System.out.println("Reading game request");
                String gameRequest = reader.readLine();
                System.out.println("Read gameRequest: " + gameRequest);
                writer.write(addrs[i] + "\n");
                writer.flush();
                System.out.println("Wrote addr: " + addrs[i]);

                // clean up the socket
                reader.close();
                writer.close();
                sock.close();

                System.out.println("Closed sockets");

                i = (i  + 1) % addrs.length;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
