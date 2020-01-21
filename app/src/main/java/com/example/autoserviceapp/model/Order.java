package com.example.autoserviceapp.model;

public class Order {
    private int id;
    private String date;
    private String work;
    private String status;
    private String comment;
    private User primaryUser;
    private Vehicle primaryVehicle;

    public Order() {
    }

    public Order(String date, String work, String status, String comment, User primaryUser, Vehicle primaryVehicle) {
        this.date = date;
        this.work = work;
        this.status = status;
        this.comment = comment;
        this.primaryUser = primaryUser;
        this.primaryVehicle = primaryVehicle;
    }

    public Order(int id, String date, String work, String status, String comment, User primaryUser, Vehicle primaryVehicle) {
        this.id = id;
        this.date = date;
        this.work = work;
        this.status = status;
        this.comment = comment;
        this.primaryUser = primaryUser;
        this.primaryVehicle = primaryVehicle;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
