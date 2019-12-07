package com.example.autoserviceapp.model;


import com.google.gson.annotations.SerializedName;

public class User {
    private int id;
    @SerializedName("username")
    private String Username;
    @SerializedName("email")
    private String Email;
    @SerializedName("password")
    private String Password;

    public User(String username, String email, String password) {
        this.Username = username;
        this.Email = email;
        this.Password = password;
    }

    public User() {
    }
    
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }
}
