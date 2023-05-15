package com.example.myapplication;

public class CommentData {

    private int profile;
    private String name;
    private String comment;

    public CommentData(int profile, String name, String comment){
        this.profile = profile;
        this.name = name;
        this.comment = comment;
    }

    public int getprofile() { return this.profile; }

    public String getname()
    {
        return this.name;
    }

    public String getcomment()
    {
        return this.comment;
    }

}
