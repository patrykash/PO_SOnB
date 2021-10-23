package com.example.sonb.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler(Socket socket) {
        clientSocket = socket;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void read() {
        try {
            String inputLine = in.readLine();
            System.out.println("Wiadomosc dostarczona na serwer : " + inputLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(int clientId) {
        out.println("Wiadomosc z serwera dla klienta : " + clientId);
    }

    public void close() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
