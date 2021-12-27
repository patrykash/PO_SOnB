package com.example.sonb.service;

import com.example.sonb.model.MainServer;
import com.example.sonb.model.ServerPort;
import com.example.sonb.model.SimpleServer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    private int serverPortNumber = ServerPort.MAIN_S.getPortNumber();
    List<SimpleServer> clientList =  new ArrayList<>(7);

    public void addClients(){
        clientList.clear();
        for (int i = 0; i < 7; i++) {
            clientList.add(new SimpleServer(ServerPort.values()[i+1].getPortNumber()));
        }
    }

    public void connectClients() {
        if (clientList.size() == 7){
            for (int i = 0; i < 7; i++) {
                try {
                    clientList.get(i).startConnection("127.0.0.1", serverPortNumber);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void connectClients(int clientId) {
        for (int i = 0; i < clientList.size(); i++) {
            if (clientId == i) {
                continue;
            }
            try {
                clientList.get(i).startConnection("127.0.0.1", serverPortNumber);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void reconnectClient(int clientId){
        try {
            clientList.get(clientId).startConnection("127.0.0.1", serverPortNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(int idClient, String message) {
        try {
            clientList.get(idClient).sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            for (SimpleServer simpleServer : clientList) {
                simpleServer.sendMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readMessage(int idClient) {
        return clientList.get(idClient).readMessage();
    }

    public List<String> readMessage() {
        List<String> messages = new ArrayList<>(7);
        for (SimpleServer simpleServer : clientList) {
            String message = simpleServer.readMessage();
            messages.add(message);
        }
        return messages;
    }

    public void stopClient(int clientId) {
        try {
            clientList.get(clientId).stopConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopClients() {
        for (int i = 0; i < clientList.size(); i++) {
            stopClient(i);
        }
    }

    public MainServer convertToMainServer(int clientId) {
        serverPortNumber = clientList.get(clientId).getPort();
        clientList.get(clientId).startT();
        return clientList.get(clientId);
    }

}
