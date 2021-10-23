package model;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class MainServer {

    private ServerSocket serverSocket;
    private int connectedClients;
    public List<ClientHandler> clients = new ArrayList<>(7);


    public void start(int port){
        try {
            serverSocket = new ServerSocket(port);
            while (connectedClients < 7) {
                clients.add(new ClientHandler(serverSocket.accept()));
                clients.get(connectedClients).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
