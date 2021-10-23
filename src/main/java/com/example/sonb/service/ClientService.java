package com.example.sonb.service;

import com.example.sonb.model.Client;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {
    List<Client> clientList =  new ArrayList<>();
    public void startClient() {
        for (int i = 0; i < 7; i++) {
            clientList.add(new Client());
            try {
                clientList.get(i).startConnection("127.0.0.1", 6666);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void sendMessage(int idClient) {
        try {
            clientList.get(idClient).sendMessage("Test : " + idClient);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMessage(int idClient) {
        String message = clientList.get(idClient).readMessage();
        System.out.println(message);
    }

    public void stopClient(int clientId) {
        try {
            clientList.get(clientId).stopConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
