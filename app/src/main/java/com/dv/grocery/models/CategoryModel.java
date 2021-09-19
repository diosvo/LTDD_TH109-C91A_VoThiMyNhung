package com.dv.grocery.models;

public class CategoryModel {
    String name;
    String group;
    String image;
    String description;

    public CategoryModel() {
    }

    public CategoryModel(String name, String group, String image, String description) {
        this.name = name;
        this.group = group;
        this.image = image;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
