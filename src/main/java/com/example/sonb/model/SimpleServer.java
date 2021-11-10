package com.example.sonb.model;

public class SimpleServer extends MainServer {
    private Client client;

    public SimpleServer(int port) {
        super(port);
    }

    public void createClient() {
        client = new Client();
    }
    public Client getClient() {
        return client;
    }
}
