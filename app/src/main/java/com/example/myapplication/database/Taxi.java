package com.example.myapplication.database;

import com.kakao.sdk.user.model.User;

public class Taxi {
    private int Admin;
    private Chat Chat;
    private int Cost;
    private String From;
    private String To;
    private User User;

    public void setCost(int cost) {
        Cost = cost;
    }

    public int getCost() {
        return Cost;
    }

    public Chat getChat() {
        return Chat;
    }

    public int getAdmin() {
        return Admin;
    }

    public String getFrom() {
        return From;
    }

    public String getTo() {
        return To;
    }

    public com.kakao.sdk.user.model.User getUser() {
        return User;
    }

    public void setAdmin(int admin) {
        Admin = admin;
    }

    public void setChat(Chat chat) {
        Chat = chat;
    }

    public void setFrom(String from) {
        From = from;
    }

    public void setTo(String to) {
        To = to;
    }

    public void setUser(com.kakao.sdk.user.model.User user) {
        User = user;
    }
    public Taxi(int Admin,Chat Chat, int Cost, String From, String To, User User){
        this.Admin=Admin;
        this.Chat=Chat;
        this.Cost=Cost;
        this.From=From;
        this.To=To;
        this.User=User;
    }
}
