package com.example.hiveapp_login.model;

import java.sql.Timestamp;

public class WorkSpace {
    private String workid;
    private String name;
    private String ownerid;
    private String confirmcode;
    private Timestamp codeexpiredat;
    private Timestamp createdat;
    private Timestamp updatedat;
    private String title;
    private String owneremail;

    public WorkSpace(String workid, String name, String title) {
        this.workid = workid;
        this.name = name;
        this.title = title;
    }

    public String getWorkid() {
        return workid;
    }

    public void setWorkid(String workid) {
        this.workid = workid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(String ownerid) {
        this.ownerid = ownerid;
    }

    public String getConfirmcode() {
        return confirmcode;
    }

    public void setConfirmcode(String confirmcode) {
        this.confirmcode = confirmcode;
    }

    public Timestamp getCodeexpiredat() {
        return codeexpiredat;
    }

    public void setCodeexpiredat(Timestamp codeexpiredat) {
        this.codeexpiredat = codeexpiredat;
    }

    public Timestamp getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Timestamp createdat) {
        this.createdat = createdat;
    }

    public Timestamp getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(Timestamp updatedat) {
        this.updatedat = updatedat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOwneremail() {
        return owneremail;
    }

    public void setOwneremail(String owneremail) {
        this.owneremail = owneremail;
    }
}