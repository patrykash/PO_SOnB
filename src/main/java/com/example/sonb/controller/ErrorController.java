package com.example.sonb.controller;

import com.example.sonb.model.MainServer;
import com.example.sonb.service.ClientService;
import com.example.sonb.service.MainServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
public class ErrorController {

    private final MainServerService mainServerService;
    private final ClientService clientService;

    @Autowired
    public ErrorController(MainServerService mainServerService, ClientService clientService) {
        this.mainServerService = mainServerService;
        this.clientService = clientService;
    }

    @GetMapping("/server")
    void runErrorWithServer() {
        clientService.stopClients();
        mainServerService.stopClientsSockets();
        mainServerService.stopMainServer();
    }

    @GetMapping("/code")
    void runErrorWithCoding() {

    }

    @GetMapping("/client")
    void runErrorWithClient() {

    }

    @GetMapping("/server/fix/{clientId}")
    void fixErrorWithServer(@PathVariable("clientId") int clientId) {
        MainServer newMainServer = clientService.convertToMainServer(clientId);
        mainServerService.setMainServer(newMainServer);
    }

    @GetMapping("/code/fix")
    void fixErrorWithCoding() {

    }

    @GetMapping("/client/fix")
    void fixErrorWithClient() {

    }

}
