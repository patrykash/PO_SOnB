package com.example.sonb.service;

import com.example.sonb.dto.MessageDto;
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
        clientList.get(idClient).sendMessage(message);
    }

    public void sendMessage(String message) {
        for (SimpleServer simpleServer : clientList) {
            simpleServer.sendMessage(message);
        }
    }

    public String readMessage(int idClient) {
        return clientList.get(idClient).readMessage();
    }

    public List<String> readMessage() {
        List<String> messages = new ArrayList<>(7);
        for (SimpleServer simpleServer : clientList) {
            if (simpleServer.isConnected()){
                String message = simpleServer.readMessage();
                messages.add(message);
            } else {
                messages.add("");
            }
        }
        return messages;
    }

    public List<MessageDto> convertMessagesToDto(List<String> messages) {
        List<MessageDto> messagesDto = new ArrayList<>();
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).isEmpty()) {
                messagesDto.add(new MessageDto(i));
            } else {
                messagesDto.add(new MessageDto(messages.get(i)));
            }
        }
        return messagesDto;
    }
    public MessageDto convertMessagesToDto(String message) {
            if (message.isEmpty()) {
                return new MessageDto();
            } else {
                return new MessageDto(message);
            }
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

    public void sendStatusMessageToServer(List<MessageDto> messageDtos) {
        String statusMessage;
        for (int i = 0; i < messageDtos.size(); i++) {
            if (messageDtos.get(i).isCorrect()){
                statusMessage = "OK";
            } else {
                statusMessage = "ERROR";
            }
            sendMessage(i,statusMessage);
        }
    }
}
