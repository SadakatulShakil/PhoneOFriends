package com.example.phoneofriends.View.Model;

public class SingleChats {
    private String senderId;
    private String receiverId;
    private String messages;
    private String sendingDate;
    private String sendingTime;

    public SingleChats() {
    }


    public SingleChats(String senderId, String receiverId, String messages, String sendingDate, String sendingTime) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.messages = messages;
        this.sendingDate = sendingDate;
        this.sendingTime = sendingTime;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getSendingDate() {
        return sendingDate;
    }

    public void setSendingDate(String sendingDate) {
        this.sendingDate = sendingDate;
    }

    public String getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(String sendingTime) {
        this.sendingTime = sendingTime;
    }
}
