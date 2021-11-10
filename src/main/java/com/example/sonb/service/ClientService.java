package com.example.sonb.service;

import com.example.sonb.model.Client;
import com.example.sonb.model.ServerPort;
import com.example.sonb.model.SimpleServer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {
    List<SimpleServer> clientList =  new ArrayList<>();
    public void startClient() {

        for (int i = 0; i < 7; i++) {
            clientList.add(new SimpleServer(ServerPort.values()[i+1].getPortNumber()));
            clientList.get(i).createClient();
            try {
                clientList.get(i).getClient().startConnection("127.0.0.1", 6666);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void sendMessage(int idClient) {
        try {
            clientList.get(idClient).getClient().sendMessage("Test : " + idClient);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMessage(int idClient) {
        String message = clientList.get(idClient).getClient().readMessage();
        System.out.println(message);
    }

    public void stopClient(int clientId) {
        try {
            clientList.get(clientId).getClient().stopConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
