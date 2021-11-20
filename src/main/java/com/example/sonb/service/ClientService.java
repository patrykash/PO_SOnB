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
    List<SimpleServer> clientList =  new ArrayList<>();
    public void startClient() {

        for (int i = 0; i < 7; i++) {
            clientList.add(new SimpleServer(ServerPort.values()[i+1].getPortNumber()));
            clientList.get(i).createClient();
            try {
                clientList.get(i).getClient().startConnection("127.0.0.1", serverPortNumber);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void reconnect() {
        for (int i = 0; i < 7; i++) {
            try {
                clientList.get(i).getClient().startConnection("127.0.0.1", serverPortNumber);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void reconnect(int clientId) {
        for (int i = 0; i < 7; i++) {
            if (clientId == i) {
                continue;
            }
            try {
                clientList.get(i).getClient().startConnection("127.0.0.1", serverPortNumber);
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

    public void sendMessage(String message) {
        try {
            for (SimpleServer simpleServer : clientList) {
                simpleServer.getClient().sendMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMessage(int idClient) {
        String message = clientList.get(idClient).getClient().readMessage();
        System.out.println(message);
    }

    public void readMessage() {
        for (SimpleServer simpleServer : clientList) {
            String message = simpleServer.getClient().readMessage();
            System.out.println(message);
            String bergerCodeFromMessage = BergerService.getBergerCodeFromMessage(message);
            Long decodedBergerCode = BergerService.decodeBerger(bergerCodeFromMessage);
            System.out.println("ilość jedynek z kodu : " + decodedBergerCode);
            Long numberOfOnesInMessage = BergerService.countNumberOfOnes(message.substring(0,16));
            System.out.println("Ilosc jedynek w wiadmości : " + numberOfOnesInMessage);
        }
    }

    public void stopClient(int clientId) {
        try {
            clientList.get(clientId).getClient().stopConnection();
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
        System.out.println(serverPortNumber);
        serverPortNumber = clientList.get(clientId).getPort();
        System.out.println(serverPortNumber);
        clientList.get(clientId).startT();
        return clientList.get(clientId);
    }

}
