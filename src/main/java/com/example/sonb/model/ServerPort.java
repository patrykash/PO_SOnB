package com.example.sonb.model;

public enum ServerPort {
    MAIN_S(6666),
    S0(6667),
    S1(6668),
    S2(6669),
    S3(6670),
    S4(6671),
    S5(6672),
    S6(6673),
    S7(6674),
    ;

    private final int PORT_NUMBER;

    ServerPort(int portNumber) {
        this.PORT_NUMBER = portNumber;
    }

    public int getPortNumber() {
        return PORT_NUMBER;
    }
}
