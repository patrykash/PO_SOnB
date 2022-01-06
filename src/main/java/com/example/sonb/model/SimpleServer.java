package com.example.sonb.model;

import java.io.IOException;

public class SimpleServer extends MainServer {
    private Client client;
    private boolean isConnected = false;

    public SimpleServer(int port) {
        super(port);
        client = new Client();
    }

    public void startConnection(String ip, int serverPortNumber) throws IOException {
        isConnected = true;
        client.startConnection(ip, serverPortNumber);
    }

    public void stopConnection() throws IOException {
        isConnected = false;
        client.stopConnection();
    }

    public void sendMessage(String message) throws IOException {
        client.sendMessage(message);
    }

    public String readMessage() {
        return client.readMessage();
    }

    public boolean isConnected() {
        return isConnected;
    }

}
