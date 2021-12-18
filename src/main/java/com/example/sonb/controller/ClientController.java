package com.example.sonb.controller;

import com.example.sonb.service.ClientService;
import com.example.sonb.service.MainServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    ResponseEntity<String> readClient(@PathVariable("clientId") int clientId) {
        return ResponseEntity.ok(clientService.readMessage(clientId));
    }

    @GetMapping("/read")
    ResponseEntity<List<String>> readClient() {
       return ResponseEntity.ok( clientService.readMessage());
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
