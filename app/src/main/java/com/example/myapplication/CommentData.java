package com.example.myapplication;

public class CommentData {

    private int poster;
    private String depart;
    private String arrive;
    private String time;
    private int headCount;
    private int price;

    public CommentData(int poster, String depart, String arrive, String time, int headCount, int price){
        this.poster = poster;
        this.depart = depart;
        this.arrive = arrive;
        this.time = time;
        this.headCount = headCount;
        this.price = price;
    }

    public int getPoster() { return this.poster; }

    public String getDepart()
    {
        return this.depart;
    }

    public String getArrive()
    {
        return this.arrive;
    }

    public String getTime()
    {
        return this.time;
    }

    public int getHeadCount() { return this.headCount; }

    public int getPrice() { return this.price; }

}
