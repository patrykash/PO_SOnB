package com.example.sonb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
public class ErrorController {

    @GetMapping("/server")
    void runErrorWithServer() {

    }

    @GetMapping("/code")
    void runErrorWithCoding() {

    }

    @GetMapping("/client")
    void runErrorWithClient() {

    }

    @GetMapping("/server/fix")
    void fixErrorWithServer() {

    }

    @GetMapping("/code/fix")
    void fixErrorWithCoding() {

    }

    @GetMapping("/client/fix")
    void fixErrorWithClient() {

    }

}
