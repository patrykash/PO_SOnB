package com.example.sonb.dto;

import com.example.sonb.service.BergerService;

public class MessageDto {

    private String message;

    private String bergerCode;

    private boolean isCorrect;

    private Integer clientId;

    public MessageDto() {
        this.message = "No connection";
        this.bergerCode = "00000";
        this.isCorrect = false;
    }

    public MessageDto(String message) {
        this.message = BergerService.getMessageContentFromMessage(message);
        this.bergerCode = BergerService.getBergerCodeFromMessage(message);
        this.isCorrect = BergerService.isMessageCorrect(message);
    }

    public MessageDto(int clientId) {
        this.clientId = clientId;
        this.message = "No connection";
        this.bergerCode = "00000";
        this.isCorrect = false;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBergerCode() {
        return bergerCode;
    }

    public void setBergerCode(String bergerCode) {
        this.bergerCode = bergerCode;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }
}
