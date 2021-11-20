package com.example.sonb.controller;

import com.example.sonb.service.ClientService;
import com.example.sonb.service.MainServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        clientService.startClient();
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
    void sendClient(@PathVariable("clientId") int clientId) {
        clientService.sendMessage(clientId);
    }

    @GetMapping("/send")
    void sendClient(@RequestBody String message) {
        clientService.sendMessage(message);
    }

    @GetMapping("/read/{clientId}")
    void readClient(@PathVariable("clientId") int clientId) {
        clientService.readMessage(clientId);
    }

    @GetMapping("/read")
    void readClient() {
        clientService.readMessage();
    }

    @GetMapping("/reconnect")
    void reconnect() {
        clientService.reconnect();
    }

    @GetMapping("/connect/{clientId}")
    void connect(@PathVariable("clientId") int clientId) {
        clientService.reconnect(clientId);
    }

}
