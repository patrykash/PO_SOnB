package com.example.sonb.controller;

import com.example.sonb.dto.MessageDto;
import com.example.sonb.service.ClientService;
import com.example.sonb.service.MainServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/client")
public class ClientController {

    private ClientService clientService;
    private MainServerService mainServerService;


    @Autowired
    public ClientController(ClientService clientService, MainServerService mainServerService) {
        this.clientService = clientService;
        this.mainServerService = mainServerService;
    }

    @GetMapping("/start")
    void startClient() {
        clientService.addClients();
        clientService.connectClients();
    }

    @GetMapping("/stop/{clientId}")
    void stopClient(@PathVariable("clientId") int clientId) {
        clientService.stopClient(clientId);
        mainServerService.stopClientSocket(clientId);
    }

    @GetMapping("/stop")
    void stopClients() {
        clientService.stopClients();
        mainServerService.stopClientsSockets();
    }

    @GetMapping("/send/{clientId}")
    void sendClient(@RequestBody String message,@PathVariable("clientId") int clientId) {
        clientService.sendMessage(clientId, message);
    }

    @GetMapping("/send")
    void sendClient(@RequestBody String message) {
        clientService.sendMessage(message);
    }

    @GetMapping("/read/{clientId}")
    ResponseEntity<MessageDto> readClient(@PathVariable("clientId") int clientId) {
        String message = clientService.readMessage(clientId);
        return ResponseEntity.ok(clientService.convertMessagesToDto(message));
    }

    @GetMapping("/read")
    ResponseEntity<List<MessageDto>> readClient() {
        List<String> messages = clientService.readMessage();
        List<MessageDto> messageDtos = clientService.convertMessagesToDto(messages);
        clientService.sendStatusMessageToServer(messageDtos);
        return ResponseEntity.ok(messageDtos);
    }

    @GetMapping("/reconnect")
    void reconnect() {
        clientService.connectClients();
    }

    @GetMapping("/connect/{clientId}")
    void connect(@PathVariable("clientId") int clientId) {
        clientService.connectClients(clientId);
    }

}
