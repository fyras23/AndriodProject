package com.mobile.authentication;

public class ShopModel {
    String name,img;

    int price;

    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public ShopModel(String name, String img, int price) {
        this.name = name;
        this.img = img;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ShopModel() {
    }

}
