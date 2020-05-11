package com.example.myapplication.models;

public class temp_user {
    private String email,type;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public temp_user(String email, String type) {
        this.email = email;
        this.type = type;
    }

    public temp_user() {
    }
}
