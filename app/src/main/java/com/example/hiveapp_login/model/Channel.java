package com.example.hiveapp_login.model;

import java.sql.Date;

public class Channel {
    private String chanid;
    private String name;
    private String workid;
    private String description;
    private Date createdat;
    private Date updatedat;

    public Channel(String chanid, String name) {
        this.chanid = chanid;
        this.name = name;
    }

    public String getChanid() {
        return chanid;
    }

    public void setChanid(String chanid) {
        this.chanid = chanid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkid() {
        return workid;
    }

    public void setWorkid(String workid) {
        this.workid = workid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }

    public Date getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(Date updatedat) {
        this.updatedat = updatedat;
    }
}