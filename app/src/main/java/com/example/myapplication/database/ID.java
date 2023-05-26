package com.example.myapplication.database;

public class ID {
    private int Cost;
    private int Count;
    private String Email;
    private String Image;
    private MySang MySang;
    private String Name;
    private String Password;
    private Review Review;
    private int Seat;
    private int Sex;

    public ID(){}

    public int getCost() {
        return Cost;
    }

    public int getCount() {
        return Count;
    }

    public String getEmail() {
        return Email;
    }

    public String getImage() {
        return Image;
    }

    public String getName() {
        return Name;
    }

    public String getPassword() {
        return Password;
    }

    public int getSeat() {
        return Seat;
    }

    public int getSex() {
        return Sex;
    }

    public MySang getMySang() {
        return MySang;
    }

    public Review getReview() {
        return Review;
    }

    public void setMySang(MySang mysang) {
        MySang = mysang;
    }

    public void setReview(Review review) {
        Review = review;
    }

    public void setCost(int cost) {
        Cost = cost;
    }

    public void setCount(int count) {
        Count = count;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setSeat(int seat) {
        Seat = seat;
    }

    public void setSex(int sex) {
        Sex = sex;
    }

    public ID(int Cost, int Count, String Email, String Image, MySang Mysang,String Name, String Password, Review Review, int Seat, int Sex){
     this.Cost=Cost;
     this.Count=Count;
     this.Email=Email;
     this.Image=Image;
     this.MySang=MySang;
     this.Name=Name;
     this.Password=Password;
     this.Review=Review;
     this.Seat=Seat;
     this.Sex=Sex;
    }
}
