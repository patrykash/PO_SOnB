package com.example.sonb.model;

public class SimpleServer extends MainServer {
    private Client client;

    public void createClient() {
        client = new Client();
    }
    public Client getClient() {
        return client;
    }
}
