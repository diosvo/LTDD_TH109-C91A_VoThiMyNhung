package com.dv.grocery.models;

import java.io.Serializable;

public class ProductModel implements Serializable {
    String name;
    String description;
    String price;
    String image;
    String group;
    String type_name;

    public ProductModel() {
    }

    public ProductModel(String name, String description, String price, String image, String group, String type_name) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.group = group;
        this.type_name = type_name;
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

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }
}
