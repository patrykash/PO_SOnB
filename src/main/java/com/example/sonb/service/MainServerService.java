package com.example.sonb.service;

import com.example.sonb.model.ClientHandler;
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
        mainServer.clients.get(clientId).send("1111111111111111");
    }

    public void sendMessage(String message) {
        String messageInBinary =  BergerService.convertStringToBinary(message);
        System.out.println("messageInBinary : " + messageInBinary);
        String bergerCode;
        if (BergerService.isIsErrorCodeActive()) {
            bergerCode = BergerService.getBergerCode(messageInBinary, 0L);
        } else {
            bergerCode = BergerService.getBergerCode(messageInBinary);
        }
        String messageWithCode = messageInBinary + bergerCode;
        mainServer.clients.forEach(it -> it.send(messageWithCode));
    }

    public void readMessage(int clientId) {
        mainServer.clients.get(clientId).read();
    }

    public void readMessage() {
        for (ClientHandler client : mainServer.clients) {
            client.read();
        }
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
