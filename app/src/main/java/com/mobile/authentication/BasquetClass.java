package com.mobile.authentication;

public class BasquetClass {
    String name,description;
    float prix;

    public BasquetClass(String name, String description, float prix) {
        this.name = name;
        this.description = description;
        this.prix = prix;
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

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }
}
