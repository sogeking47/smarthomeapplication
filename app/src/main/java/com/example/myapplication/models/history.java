package com.example.myapplication.models;

public class history {
    String action,username;

    public history() {
    }

    public history(String action, String username) {
        this.action = action;
        this.username = username;
    }

    @Override
    public String toString() {
        return "history{" +
                "action='" + action + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
