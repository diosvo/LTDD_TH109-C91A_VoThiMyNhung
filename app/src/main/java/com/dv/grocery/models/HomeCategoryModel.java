package com.dv.grocery.models;

public class HomeCategoryModel {
    String name;
    String group;
    String image;

    public HomeCategoryModel() {
    }

    public HomeCategoryModel(String name, String group, String image) {
        this.name = name;
        this.group = group;
        this.image = image;
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
}
