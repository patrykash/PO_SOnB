package com.example.sonb.service;

import com.example.sonb.model.MainServer;
import org.springframework.stereotype.Service;

@Service
public class MainServerService {
    MainServer mainServer;

    public void startMainServer() {
        mainServer = new MainServer();
        mainServer.start(6666);
    }

    public void stopMainServer() {
        mainServer.stop();
    }

    public void sendMessage(int clientId) {
        mainServer.clients.get(clientId).send(clientId);
    }

    public void readMessage(int clientId) {
        mainServer.clients.get(clientId).read();
    }
}
