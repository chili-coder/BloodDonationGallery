package com.sohaghlab.blooddonationgallery.Model;

public class User {
    String name,phone,email,city,bloodgroup,profileimageurl,type,search,id,userId,age,lastdonation;
    String status,datetitle;

    public User() {
    }

    public User(String name, String phone, String email, String city, String bloodgroup, String profileimageurl,
                String type, String search, String id, String userId, String age,
                String lastdonation,String status, String datetitle) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.city = city;
        this.bloodgroup = bloodgroup;
        this.profileimageurl = profileimageurl;
        this.type = type;
        this.search = search;
        this.id = id;
        this.userId = userId;
        this.age = age;
        this.lastdonation = lastdonation;
        this.status = status;
        this.datetitle = datetitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDatetitle() {
        return datetitle;
    }

    public void setDatetitle(String datetitle) {
        this.datetitle = datetitle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getProfileimageurl() {
        return profileimageurl;
    }

    public void setProfileimageurl(String profileimageurl) {
        this.profileimageurl = profileimageurl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLastdonation() {
        return lastdonation;
    }

    public void setLastdonation(String lastdonation) {
        this.lastdonation = lastdonation;
    }
}
