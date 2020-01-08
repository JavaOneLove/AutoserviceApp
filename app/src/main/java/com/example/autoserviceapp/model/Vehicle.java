package com.example.autoserviceapp.model;

public class Vehicle {

    private int id;
    private String mark;
    private String model;
    private String color;
    private String productionDate;
    private User primaryUser;

    public Vehicle( String mark, String model, String color, String productionDate, User primaryUser) {
        this.mark = mark;
        this.model = model;
        this.color = color;
        this.productionDate = productionDate;
        this.primaryUser = primaryUser;
    }

    public Vehicle(int id, String mark, String model, String color, String productionDate, User primaryUser) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.color = color;
        this.productionDate = productionDate;
        this.primaryUser = primaryUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public User getPrimaryUser() {
        return primaryUser;
    }

    public void setPrimaryUser(User primaryUser) {
        this.primaryUser = primaryUser;
    }
}
