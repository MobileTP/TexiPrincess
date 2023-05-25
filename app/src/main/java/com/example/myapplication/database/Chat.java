package com.example.myapplication.database;

public class Chat {
    private String Content;
    private int id;
    private String Time;

    public int getId() {
        return id;
    }

    public String getContent() {
        return Content;
    }

    public String getTime() {
        return Time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        Content = content;
    }

    public void setTime(String time) {
        Time = time;
    }
    public Chat(String Content,int id, String Time){
        this.Content=Content;
        this.id=id;
        this.Time=Time;
    }
}
