package com.example.sonb.service;

import com.example.sonb.model.ClientHandler;
import com.example.sonb.model.MainServer;
import com.example.sonb.model.ServerPort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class MainServerService {

    private MainServer mainServer;

    private String actualMessage = "  ";

    public void setMainServer(MainServer mainServer) {
        this.mainServer = mainServer;
    }

    public void startMainServer() {
        mainServer = new MainServer(ServerPort.MAIN_S.getPortNumber());
        mainServer.start();
    }

    public void startMainServer(int port) {
        mainServer = new MainServer(port);
        mainServer.start();
    }

    public void stopMainServer() {
        mainServer.stop();
    }

    public void sendMessage(int clientId,String message) {
        this.sendMessage(mainServer.clients.get(clientId),message);
    }

    public void sendMessage(String message) {
        actualMessage = message;
        mainServer.clients.forEach(it -> this.sendMessage(it,message));
    }

    private void sendMessage(ClientHandler client, String message) {
        Random rand = new Random(); //instance of random class
        int int_random = rand.nextInt(2);
        String messageInBinary =  BergerService.convertStringToBinary(message);
        String bergerCode;
        /*if (BergerService.isErrorCodeActive()) {
            bergerCode = BergerService.createBergerCode(messageInBinary, 0L);
        } else {
            bergerCode = BergerService.createBergerCode(messageInBinary);
        }*/
        bergerCode = BergerService.createBergerCode(messageInBinary);
        if (BergerService.isErrorCodeActive()){
            if (int_random == 1) {
                messageInBinary =  BergerService.convertToMessageWithError(messageInBinary);
            }
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
            String statusMessage = client.read();
            if (statusMessage.equals("ERROR")) {
                BergerService.setIsErrorCodeActive(false);

            }
            messages.add(statusMessage);
        }
        return messages;
    }

    public void startClientSocket(int clientId) {
        mainServer.reconnect(clientId);
        mainServer.increaseClientNumbers();
    }

    public void stopClientSocket(int clientId) {
        mainServer.clients.get(clientId).close();
        mainServer.clients.get(clientId).interrupt();
        mainServer.reduceClientNumbers();
    }

    public void stopClientsSockets() {
        for (int i = 0; i < mainServer.clients.size(); i++) {
            stopClientSocket(i);
        }
    }

    public void restart() {
        stopClientsSockets();
        stopMainServer();
        BergerService.setIsErrorCodeActive(false);
        startMainServer(mainServer.getPort());
    }

    public void reSendMessage(List<String> statusMessages) {
        for (int i = 0; i < statusMessages.size() ; i++) {
            if (statusMessages.get(i).equals("ERROR")) {
                sendMessage(i, actualMessage);
            }
        }
    }
}
