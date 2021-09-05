package com.sohaghlab.blooddonationgallery.Model;

public class NotificationModel {

    String title;
    String des;
    String date;
    String img;

    public NotificationModel() {
    }

    public NotificationModel(String title, String des, String date, String img) {
        this.title = title;
        this.des = des;
        this.date = date;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
