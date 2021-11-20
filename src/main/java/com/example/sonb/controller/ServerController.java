package com.example.sonb.controller;

import com.example.sonb.service.MainServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/server")
public class ServerController {

    private final MainServerService mainServerService;

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

    @GetMapping("/send/{clientId}")
    void send(@PathVariable("clientId") int clientId) {
        mainServerService.sendMessage(clientId);
    }

    @GetMapping("/send")
    void send(@RequestBody String message) {
        mainServerService.sendMessage(message);
    }

    @GetMapping("read/{clientId}")
    void read(@PathVariable("clientId") int clientId) {
        mainServerService.readMessage(clientId);
    }

    @GetMapping("read")
    void read() {
        mainServerService.readMessage();
    }

    @GetMapping("/reconnect")
    void reClient() {
        mainServerService.reconnect();
    }
}
