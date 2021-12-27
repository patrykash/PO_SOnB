package com.example.sonb.service;

import com.example.sonb.model.ServerPort;
import com.example.sonb.model.SimpleServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    final String IP = "127.0.0.1";
    final int SERVER_PORT_NUMBER = ServerPort.MAIN_S.getPortNumber();
    final String MESSAGE = "Message";
    ClientService clientService = new ClientService();

    @Test
    void shouldAddSevenClientsToClientListWhenClientListIsEmpty() {
        clientService.addClients();

        assertThat(clientService.clientList.size()).isEqualTo(7);
    }

    @Test
    void shouldAddNewClientsToClientListWhenClientListIsNotEmpty() {
        clientService.addClients();
        List<SimpleServer> oldClients = new ArrayList<>(7);
        oldClients.addAll(clientService.clientList);
        clientService.addClients();

        assertThat(clientService.clientList.size()).isEqualTo(7);
        assertThat(clientService.clientList).doesNotContainAnyElementsOf(oldClients);
    }

    @Test
    void shouldStartConnectionForEachClientsWhenClientListSizeIsCorrect() {
        SimpleServer client = mock(SimpleServer.class);
        clientService.clientList = Arrays.asList(client, client, client, client, client, client, client);

        clientService.connectClients();
        try {
            verify(client, times(7)).startConnection(IP, SERVER_PORT_NUMBER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void shouldNotStartConnectionForClientsWhenItIdIsPassed() {
        SimpleServer client = mock(SimpleServer.class);
        SimpleServer client2 = mock(SimpleServer.class);
        clientService.clientList = createClients();

        clientService.connectClients(2);
        try {
            verify(clientService.clientList.get(0), times(1)).startConnection(IP, SERVER_PORT_NUMBER);
            verify(clientService.clientList.get(1), times(1)).startConnection(IP, SERVER_PORT_NUMBER);
            verify(clientService.clientList.get(2), times(0)).startConnection(IP, SERVER_PORT_NUMBER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void shouldSendMessageForEachClients(){
        clientService.clientList = createClients();

        clientService.sendMessage(MESSAGE);

        try {
            verify(clientService.clientList.get(0), times(1)).sendMessage(MESSAGE);
            verify(clientService.clientList.get(1), times(1)).sendMessage(MESSAGE);
            verify(clientService.clientList.get(2), times(1)).sendMessage(MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void shouldSendMessageForChosenClient(){
        clientService.clientList = createClients();

        clientService.sendMessage(1,MESSAGE);

        try {
            verify(clientService.clientList.get(0), times(0)).sendMessage(MESSAGE);
            verify(clientService.clientList.get(1), times(1)).sendMessage(MESSAGE);
            verify(clientService.clientList.get(2), times(0)).sendMessage(MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void shouldReadMessageForEachClients(){
        clientService.clientList = createClients();
        when(clientService.clientList.get(0).readMessage()).thenReturn(MESSAGE);
        when(clientService.clientList.get(1).readMessage()).thenReturn(MESSAGE);
        when(clientService.clientList.get(2).readMessage()).thenReturn(MESSAGE);

        assertThat(clientService.readMessage())
                .hasSize(3)
                .contains(MESSAGE, MESSAGE, MESSAGE);
    }

    @Test
    void shouldStopAllClients(){
        clientService.clientList = createClients();

        clientService.stopClients();

        try {
            verify(clientService.clientList.get(0), times(1)).stopConnection();
            verify(clientService.clientList.get(1), times(1)).stopConnection();
            verify(clientService.clientList.get(2), times(1)).stopConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void shouldStopChosenClient(){
        clientService.clientList = createClients();

        clientService.stopClient(1);

        try {
            verify(clientService.clientList.get(0), times(0)).stopConnection();
            verify(clientService.clientList.get(1), times(1)).stopConnection();
            verify(clientService.clientList.get(2), times(0)).stopConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void shouldStartConnectionForChosenClient() {
        clientService.clientList = createClients();

        clientService.reconnectClient(1);

        try {
            verify(clientService.clientList.get(0), times(0)).startConnection(IP, SERVER_PORT_NUMBER);
            verify(clientService.clientList.get(1), times(1)).startConnection(IP, SERVER_PORT_NUMBER);
            verify(clientService.clientList.get(2), times(0)).startConnection(IP, SERVER_PORT_NUMBER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    List<SimpleServer> createClients() {
        SimpleServer client = mock(SimpleServer.class);
        SimpleServer client2 = mock(SimpleServer.class);
        SimpleServer client3 = mock(SimpleServer.class);

        return Arrays.asList(client, client2, client3);
    }
}