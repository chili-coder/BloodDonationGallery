package com.sohaghlab.blooddonationgallery.Model;

import java.io.Serializable;

public class Campaign implements Serializable {

    String id, time,date, title,des, img;
    boolean visivility;

    public Campaign() {
    }

    public Campaign(String id, String time, String date, String title, String des, String img) {
        this.id = id;
        this.time = time;
        this.date = date;
        this.title = title;
        this.des = des;
        this.img = img;
        this.visivility=false;
    }

    public boolean isVisivility() {
        return visivility;
    }

    public void setVisivility(boolean visivility) {
        this.visivility = visivility;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
