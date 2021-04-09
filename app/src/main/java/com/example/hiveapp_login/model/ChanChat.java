package com.example.hiveapp_login.model;

import java.sql.Timestamp;

public class ChanChat {
    private String chatseq;
    private String chanid;
    private String sendername;
    private String senderid;
    private String senderavata;
    private String content;
    private String day;
    private String time;
    private Timestamp createdat;
    private Timestamp updatedat;

    public ChanChat(String content) {
        this.content = content;
    }

    public ChanChat(String sendername, String senderid, String senderavata, String content, String day, String time) {
        this.sendername = sendername;
        this.senderid = senderid;
        this.senderavata = senderavata;
        this.content = content;
        this.day = day;
        this.time = time;
    }

    public String getChatseq() {
        return chatseq;
    }

    public void setChatseq(String chatseq) {
        this.chatseq = chatseq;
    }

    public String getChanid() {
        return chanid;
    }

    public void setChanid(String chanid) {
        this.chanid = chanid;
    }

    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getSenderavata() {
        return senderavata;
    }

    public void setSenderavata(String senderavata) {
        this.senderavata = senderavata;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
}
