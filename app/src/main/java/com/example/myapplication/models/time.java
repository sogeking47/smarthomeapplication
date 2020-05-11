package com.example.myapplication.models;

public class time {

    private String time;
    private String username;
    private String action;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public time(String time, String name, String action) {
        this.time = time;
        this.username = name;
        this.action = action;
    }

    public time() {
    }
}
