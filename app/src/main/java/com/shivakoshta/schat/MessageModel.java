package com.shivakoshta.schat;

public class MessageModel {
    private String msgID;
    private String senderID;
    private String message;

    public MessageModel(String msgID, String senderID, String message) {
        this.msgID = msgID;
        this.senderID = senderID;
        this.message = message;
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
