package com.mobile.authentication;

import java.util.Date;

public class Commandeclass {
    private String nom;
    private String prenom;
    private String adesse;
    private String telephone;
    private double longitude;
    private double latitude;
    private  String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNameSK() {
        return nameSK;
    }

    public void setNameSK(String nameSK) {
        this.nameSK = nameSK;
    }

    private String nameSK;

    private int price;

    public int getPrice() {
        return price;
    }

    private String UserId;
    private Date date;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Commandeclass() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Commandeclass(String nom, String prenom, String city, String telephone, double longitude, double latitude, String nameSK, int price, String userId, String img,Date date) {
        this.nom = nom;
        this.prenom = prenom;
        this.adesse = city;
        this.telephone = telephone;
        this.longitude = longitude;
        this.latitude = latitude;
        this.nameSK = nameSK;
        this.price = price;
        UserId = userId;
        this.img = img;
        this.date=date;

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdesse() {
        return adesse;
    }

    public void setAdesse(String adesse) {
        this.adesse = adesse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
