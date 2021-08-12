package com.btcsc.project;

import java.io.Serializable;
import java.util.ArrayList;

public class Planning implements Serializable {
    String subject;
    String plan;
    String date;
    String time;
    int id;
    int status;
    String list;


    public Planning(String subject, String plan, String date, String time) {
        this.subject = subject;
        this.plan = plan;
        this.date = date;
        this.time = time;
    }

    public Planning(String subject, String plan, String date, String time, int status) {
        this.subject = subject;
        this.plan = plan;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public Planning(String subject, String plan, String date, String time, int status, String list) {
        this.subject = subject;
        this.plan = plan;
        this.date = date;
        this.time = time;
        this.status = status;
        this.list = list;
    }

    public Planning(String subject, String plan, String date, String time, int id, int status, String list) {
        this.subject = subject;
        this.plan = plan;
        this.date = date;
        this.time = time;
        this.id = id;
        this.status = status;
        this.list = list;
    }

    public Planning() {
    }

    @Override
    public String toString() {
        return "Planning{" +
                "subject='" + subject + '\'' +
                ", plan='" + plan + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", list='" + list + '\'' +
                '}';
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
