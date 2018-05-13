package com.example.asus.parkalot.model;

/**
 * Created by ASUS on 16/03/2018.
 */

public class ParkingPlaces {

    private String name ;
    //private String description;
    private String rating ;
    //private int nb_episode;
    private String image_url;
    private String categorie;
    //private String studio;

    public ParkingPlaces() {
    }

    public ParkingPlaces(String name, String rating, String image_url, String categorie) {
        this.name = name;
        this.rating = rating;
        this.image_url = image_url;
        this.categorie = categorie;

    }


    public String getName() {
        return name;
    }


    public String getRating() {
        return rating;
    }


    public String getImage_url() {
        return image_url;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }


    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

}
