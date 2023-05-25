package com.example.myapplication.database;

public class MySang {
    private int[] Add(int[] origin, int idx){
        int[] newarr=new int[origin.length+1];
        for(int i=0; i<origin.length; i++)
            newarr[i]=origin[i];

        newarr[origin.length]=idx;
        return newarr;
    }
    private int[] mysang;

    public MySang(){}

    public void setMySang(int[] mysang) {
        this.mysang = mysang;
    }

    public int[] getMySang() {
        return mysang;
    }

    public MySang(int[] mysang){
        this.mysang=mysang;
    }

}
