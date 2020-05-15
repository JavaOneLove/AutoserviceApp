package com.example.autoserviceapp.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {
    private Long id;
    @SerializedName("username")
    private String Username;
    @SerializedName("email")
    private String Email;
    @SerializedName("password")
    private String Password;

    private String token;

    private List<Role> roles;

    public User(Long id, String username, String email, String password, String token, Role role) {
        this.id = id;
        Username = username;
        Email = email;
        Password = password;
        this.token = token;
        this.roles = roles;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public User(String username, String email, String password) {
        this.Username = username;
        this.Email = email;
        this.Password = password;
    }

    public User(Long id, String username, String email, String password) {
        this.id = id;
        Username = username;
        Email = email;
        Password = password;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
