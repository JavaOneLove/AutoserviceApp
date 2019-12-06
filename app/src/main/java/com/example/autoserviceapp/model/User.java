package com.example.autoserviceapp.model;


import com.google.gson.annotations.SerializedName;

public class User {
    private String Username;
    @SerializedName("email")
    private String Email;
    @SerializedName("password")
    private String Password;

    public User(String email, String password) {
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
}
