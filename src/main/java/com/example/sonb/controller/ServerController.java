package com.example.sonb.controller;

import com.example.sonb.service.MainServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server")
public class ServerController {

    private MainServerService mainServerService;

    @Autowired
    public ServerController(MainServerService mainServerService) {
        this.mainServerService = mainServerService;
    }

    @GetMapping("/start")
    void startMainServer() {
        mainServerService.startMainServer();
    }

    @GetMapping("/stop")
    void stopMainServer() {
        mainServerService.stopMainServer();
    }

    @GetMapping("send/{clientId}")
    void send(@PathVariable("clientId") int clientId) {
        mainServerService.sendMessage(clientId);
    }

    @GetMapping("read/{clientId}")
    void read(@PathVariable("clientId") int clientId) {
        mainServerService.readMessage(clientId);
    }
}
