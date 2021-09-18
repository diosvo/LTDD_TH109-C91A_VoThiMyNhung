package com.dv.grocery.models;

public class PopularProductModel {
    String name;
    String description;
    String price;
    String image;
    String group;

    public PopularProductModel() {
    }

    public PopularProductModel(String name, String description, String price, String image, String group) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
