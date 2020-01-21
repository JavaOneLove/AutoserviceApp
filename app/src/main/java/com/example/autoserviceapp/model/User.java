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

    private String role;

    public User(int id, String username, String email, String password, String role) {
        this.id = id;
        Username = username;
        Email = email;
        Password = password;
        this.role = role;
    }

    public User(String username, String email, String password) {
        this.Username = username;
        this.Email = email;
        this.Password = password;
    }

    public User(int id, String username, String email, String password) {
        this.id = id;
        Username = username;
        Email = email;
        Password = password;
    }

    public User() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
