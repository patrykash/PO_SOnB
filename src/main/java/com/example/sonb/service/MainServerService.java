package com.example.sonb.service;

import com.example.sonb.model.ClientHandler;
import com.example.sonb.model.MainServer;
import com.example.sonb.model.ServerPort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainServerService {

    private MainServer mainServer;

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

    public void sendMessage(int clientId,String message) {
        this.sendMessage(mainServer.clients.get(clientId),message);
    }

    public void sendMessage(String message) {
        mainServer.clients.forEach(it -> this.sendMessage(it,message));
    }

    private void sendMessage(ClientHandler client, String message) {
        String messageInBinary =  BergerService.convertStringToBinary(message);
        String bergerCode;
        if (BergerService.isErrorCodeActive()) {
            bergerCode = BergerService.getBergerCode(messageInBinary, 0L);
        } else {
            bergerCode = BergerService.getBergerCode(messageInBinary);
        }
        String messageWithCode = messageInBinary + bergerCode;
        client.send(messageWithCode);
    }

    public String readMessage(int clientId) {
        return mainServer.clients.get(clientId).read();
    }

    public List<String> readMessage() {
        List<String> messages = new ArrayList<>(7);
        for (ClientHandler client : mainServer.clients) {
            messages.add(client.read());
        }
        return messages;
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
