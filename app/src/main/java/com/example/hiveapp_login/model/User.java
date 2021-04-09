package com.example.hiveapp_login.model;

import java.sql.Timestamp;

public class User {
    private String userid;
    private String email;
    private String name;
    private String pwd;
    private String phone;
    private String job;
    private String photo;
    private Timestamp createdat;
    private Timestamp updatedat;
    private String lastwork;
    private String lastchannel;
    private String lastmessage;

    public User(String userid, String name, String photo) {
        this.userid = userid;
        this.name = name;
        this.photo = photo;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}


