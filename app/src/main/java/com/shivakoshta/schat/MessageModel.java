package com.shivakoshta.schat;

public class MessageModel {
    private String msgID;
    private String senderID;
    private String message;
    private String name;
    private long time;

    public MessageModel(String msgID, String senderID, String message,String name,long time) {
        this.msgID = msgID;
        this.senderID = senderID;
        this.message = message;
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MessageModel() {
    }

    public String getMsgID() {
        return msgID;
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
