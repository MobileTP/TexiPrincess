package com.example.myapplication.database;

public class User {
    private int[] Add(int[] origin, int idx){
        int[] newarr=new int[origin.length+1];
        for(int i=0; i<origin.length; i++)
            newarr[i]=origin[i];

        newarr[origin.length]=idx;
        return newarr;
    }
    private int[] user;

    public void setUser(int[] user) {
        this.user = user;
    }

    public int[] getUser() {
        return user;
    }
    public User(int[] user){
        this.user=user;
    }
}
