package com.example.sisa;

public class UserModel {
    String id,username,password,role,date;

    public UserModel(String id, String username, String password, String role, String date) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getDate() {
        return date;
    }
}
