package com.example.myapplication.database;

public class Review {
    private int idx0;
    private int idx1;
    private int idx2;
    private int idx3;
    private int idx4;
    private int idx5;

    public int getIdx0() {
        return idx0;
    }

    public int getIdx1() {
        return idx1;
    }

    public int getIdx2() {
        return idx2;
    }

    public int getIdx3() {
        return idx3;
    }

    public int getIdx4() {
        return idx4;
    }

    public int getIdx5() {
        return idx5;
    }
    public int[] getAll(){
        int[] newarr={idx0,idx1,idx2,idx3,idx4,idx5};
        return newarr;
    }

    public void setIdx0(int idx0) {
        this.idx0 = idx0;
    }

    public void setIdx1(int idx1) {
        this.idx1 = idx1;
    }

    public void setIdx2(int idx2) {
        this.idx2 = idx2;
    }

    public void setIdx3(int idx3) {
        this.idx3 = idx3;
    }

    public void setIdx4(int idx4) {
        this.idx4 = idx4;
    }

    public void setIdx5(int idx5) {
        this.idx5 = idx5;
    }
    public Review(int idx0, int idx1, int idx2, int idx3, int idx4, int idx5){
        this.idx0=idx0;
        this.idx1=idx1;
        this.idx2=idx2;
        this.idx3=idx3;
        this.idx4=idx4;
        this.idx5=idx5;
    }
}
