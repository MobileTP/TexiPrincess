package com.example.myapplication.comment;

public class CommentData {

    private int profile;
    private String name;
    private String comment;
    private int ID;
    private String time;
    public CommentData(int profile, String name, String comment,int ID, String time){
        this.profile = profile;
        this.name = name;
        this.comment = comment;
        this.ID=ID;
        this.time=time;
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
    public int getID(){return this.ID;}
    public String getTime(){return this.time;}

}
