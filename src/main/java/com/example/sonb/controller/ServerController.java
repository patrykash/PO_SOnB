package com.example.sonb.controller;

import com.example.sonb.service.MainServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    void send(@PathVariable("clientId") int clientId,@RequestBody String message) {
        mainServerService.sendMessage(clientId, message);
    }

    @GetMapping("/send")
    void send(@RequestBody String message) {
        mainServerService.sendMessage(message);
    }

    @GetMapping("read/{clientId}")
    ResponseEntity<String> read(@PathVariable("clientId") int clientId) {
        return ResponseEntity.ok(mainServerService.readMessage(clientId));
    }

    @GetMapping("read")
    ResponseEntity<List<String>> read() {
        return ResponseEntity.ok(mainServerService.readMessage());
    }

    @GetMapping("/reconnect")
    void reClient() {
        mainServerService.reconnect();
    }
}
