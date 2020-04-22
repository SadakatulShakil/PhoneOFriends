package com.example.phoneofriends.View.Model;

public class GroupChats {
    private String groupId;
    private String senderId;
    private String messages;
    private String sendingDate;
    private String sendingTime;

    public GroupChats(String groupId, String senderId, String messages, String sendingDate, String sendingTime) {
        this.groupId = groupId;
        this.senderId = senderId;
        this.messages = messages;
        this.sendingDate = sendingDate;
        this.sendingTime = sendingTime;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
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
