package com.example.myapplication.models;

public class user {
    private String name,email,user_type;

    public user() {
    }

    public user(String name, String email, String user_type) {
        this.name = name;
        this.email = email;
        this.user_type = user_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
