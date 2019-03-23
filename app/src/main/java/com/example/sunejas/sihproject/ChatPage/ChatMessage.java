package com.example.sunejas.sihproject.ChatPage;

public class ChatMessage {

    public enum ItemType {
        ONE_ITEM, TWO_ITEM;
    }
    private String body, creatonOn,sender,sentTo;
    private ItemType type;


    public ChatMessage() {
    }

    public ChatMessage(String body, String creatonOn, String sender, String sentTo, ItemType type) {
        this.body = body;
        this.creatonOn = creatonOn;
        this.sender = sender;
        this.sentTo = sentTo;
        this.type = type;

    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreatonOn() {
        return creatonOn;
    }

    public void setCreatonOn(String creatonOn) {
        this.creatonOn = creatonOn;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSentTo() {
        return sentTo;
    }

    public void setSentTo(String sentTo) {
        this.sentTo = sentTo;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }
}

