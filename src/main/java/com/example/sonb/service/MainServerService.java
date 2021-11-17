package com.example.sonb.service;

import com.example.sonb.model.MainServer;
import com.example.sonb.model.ServerPort;
import org.springframework.stereotype.Service;

@Service
public class MainServerService {

    MainServer mainServer;

    public void setMainServer(MainServer mainServer) {
        this.mainServer = mainServer;
    }

    public void startMainServer() {
        mainServer = new MainServer(ServerPort.MAIN_S.getPortNumber());
        mainServer.start();
    }

    public void stopMainServer() {
        mainServer.stop();
    }

    public void sendMessage(int clientId) {
        System.out.println("MainServer");
        mainServer.clients.get(clientId).send(clientId);
    }

    public void readMessage(int clientId) {
        System.out.println("MainServer");
        mainServer.clients.get(clientId).read();
    }

    public void stopClientSocket(int clientId) {
        mainServer.clients.get(clientId).close();
        mainServer.reduceClientNumbers();
    }

    public void stopClientsSockets() {
        for (int i = 0; i < mainServer.clients.size(); i++) {
            stopClientSocket(i);
        }
    }

    public void reconnect() {
        mainServer.reconnect();
    }
}
