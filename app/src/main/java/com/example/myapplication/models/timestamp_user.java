package com.example.myapplication.models;

public class timestamp_user
{
    private String username,action,time;

    public String getUsername() {
        return username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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


    public timestamp_user(String username, String action, String time) {
        this.username = username;
        this.action = action;
        this.time = time;
    }

    public timestamp_user() {
    }
}
