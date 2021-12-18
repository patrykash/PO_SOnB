package com.example.sonb.service;

import com.example.sonb.model.ClientHandler;
import com.example.sonb.model.MainServer;
import com.example.sonb.model.ServerPort;
import com.example.sonb.model.SimpleServer;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class MainServerServiceTest {

    final String MESSAGE = "AB";
    final String MESSAGE_IN_BINARY = "0100000101000010";
    final String CORRECT_MESSAGE_WITH_CODE = "010000010100001011011";
    final String INCORRECT_MESSAGE_WITH_CODE = "010000010100001000100";

    MainServerService mainServerService = new MainServerService();
    MainServer mainServer = new MainServer(ServerPort.MAIN_S.getPortNumber());

    @Test
    void shouldSendMessageWithCorrectCodeForEachClientsWhenErrorCodeIsNotActive() {
        mainServer.clients = createClients();
        mainServerService.setMainServer(mainServer);
        MockedStatic<BergerService> bergerService = Mockito.mockStatic(BergerService.class);

        bergerService.when(() -> BergerService.convertStringToBinary(MESSAGE)).thenReturn("0100000101000010");
        bergerService.when(BergerService::isErrorCodeActive).thenReturn(false);
        bergerService.when(() -> BergerService.getBergerCode(MESSAGE_IN_BINARY)).thenReturn("11011");

        mainServerService.sendMessage(MESSAGE);

        verify(mainServer.clients.get(0), times(1)).send(CORRECT_MESSAGE_WITH_CODE);
        verify(mainServer.clients.get(1), times(1)).send(CORRECT_MESSAGE_WITH_CODE);
        verify(mainServer.clients.get(2), times(1)).send(CORRECT_MESSAGE_WITH_CODE);
        bergerService.close();
    }

    @Test
    void shouldSendMessageWithWrongCodeForEachClientsWhenErrorCodeIsNotActive() {
        mainServer.clients = createClients();
        mainServerService.setMainServer(mainServer);
        MockedStatic<BergerService> bergerService = Mockito.mockStatic(BergerService.class);

        bergerService.when(() -> BergerService.convertStringToBinary(MESSAGE)).thenReturn("0100000101000010");
        bergerService.when(BergerService::isErrorCodeActive).thenReturn(true);
        bergerService.when(() -> BergerService.getBergerCode(MESSAGE_IN_BINARY,0L)).thenReturn("00100");

        mainServerService.sendMessage(MESSAGE);

        verify(mainServer.clients.get(0), times(1)).send(INCORRECT_MESSAGE_WITH_CODE);
        verify(mainServer.clients.get(1), times(1)).send(INCORRECT_MESSAGE_WITH_CODE);
        verify(mainServer.clients.get(2), times(1)).send(INCORRECT_MESSAGE_WITH_CODE);
        bergerService.close();
    }

    @Test
    void shouldReadMessageForEachClients(){
        mainServer.clients = createClients();
        mainServerService.setMainServer(mainServer);

        when(mainServer.clients.get(0).read()).thenReturn(MESSAGE);
        when(mainServer.clients.get(1).read()).thenReturn(MESSAGE);
        when(mainServer.clients.get(2).read()).thenReturn(MESSAGE);

        assertThat(mainServerService.readMessage())
                .hasSize(3)
                .contains(MESSAGE, MESSAGE, MESSAGE);
    }

    List<ClientHandler> createClients() {
        ClientHandler client = mock(ClientHandler.class);
        ClientHandler client2 = mock(ClientHandler.class);
        ClientHandler client3 = mock(ClientHandler.class);

        return Arrays.asList(client, client2, client3);
    }
}