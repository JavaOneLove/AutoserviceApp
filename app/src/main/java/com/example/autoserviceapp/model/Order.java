package com.example.autoserviceapp.model;

import java.util.Date;

public class Order {
    private int id;
    private Date date;
    private String status;
    private User primaryUser;
    private Vehicle primaryVehicle;

    public Order() {
    }

    public Order(Date date, String status, User primaryUser, Vehicle primaryVehicle) {
        this.date = date;
        this.status = status;
        this.primaryUser = primaryUser;
        this.primaryVehicle = primaryVehicle;
    }

    public Order(int id, Date date, String status, User primaryUser, Vehicle primaryVehicle) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.primaryUser = primaryUser;
        this.primaryVehicle = primaryVehicle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getPrimaryUser() {
        return primaryUser;
    }

    public void setPrimaryUser(User primaryUser) {
        this.primaryUser = primaryUser;
    }

    public Vehicle getPrimaryVehicle() {
        return primaryVehicle;
    }

    public void setPrimaryVehicle(Vehicle primaryVehicle) {
        this.primaryVehicle = primaryVehicle;
    }
}
