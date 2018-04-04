package com.example.hshakilst.pubnubchat;

/**
 * Created by hshakilst on 6/20/2017.
 */

public class Message {
    private String content;
    private boolean isMine;
    private String userName;

    public Message(String userName, String content, boolean isMine) {
        this.userName = userName;
        this.content = content;
        this.isMine = isMine;
    }

    public String getContent() {
        return content;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setIsMine(boolean bool){ this.isMine = bool; }

    public String getUserInitial(){
        if(!isMine())
            return userName.substring(0,1).toUpperCase() + userName.substring(1,2);
        else
            return "Me";
    }

    public  String getUserName(){
        return userName;
    }
}
