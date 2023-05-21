package com.example.myapplication;

import java.util.List;

public class ResultSearchKeyword {
    private List<Place> documents; // search results

    public ResultSearchKeyword(List<Place> documents) {
        this.documents = documents;
    }

    public List<Place> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Place> documents) {
        this.documents = documents;
    }
}

class Place {
    private String place_name; // place name, business name
    private String address_name; // full lot number address
    private String road_address_name; // full road name address
    private String x; // X coordinate value or longitude
    private String y; // Y coordinate value or latitude

    public Place(String place_name, String address_name, String road_address_name, String x, String y) {
        this.place_name = place_name;
        this.address_name = address_name;
        this.road_address_name = road_address_name;
        this.x = x;
        this.y = y;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getAddress_name() {
        return address_name;
    }

    public void setAddress_name(String address_name) {
        this.address_name = address_name;
    }

    public String getRoad_address_name() {
        return road_address_name;
    }

    public void setRoad_address_name(String road_address_name) {
        this.road_address_name = road_address_name;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }
}

