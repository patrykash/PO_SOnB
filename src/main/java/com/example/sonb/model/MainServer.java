package com.example.sonb.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class MainServer {

    private ServerSocket serverSocket;
    private int connectedClients = 0;
    private int port;
    public List<ClientHandler> clients = new ArrayList<>(7);

    public MainServer(int port) {
        this.port = port;
    }

    public void start(){
        try {
            serverSocket = new ServerSocket(port);
            while (connectedClients < 7) {
                clients.add(new ClientHandler(serverSocket.accept()));
                clients.get(connectedClients).start();
                connectedClients++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
