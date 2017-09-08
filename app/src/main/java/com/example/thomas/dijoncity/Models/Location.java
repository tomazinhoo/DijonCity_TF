package com.example.thomas.dijoncity.Models;

/**
 * Created by Thomas on 08/09/2017.
 */

public class Location {

    private String adress;
    private int postalCode;
    private String city;
    private Position position;

    public Location(String adress, int postalCode, String city, Position position) {
        this.adress = adress;
        this.postalCode = postalCode;
        this.city = city;
        this.position = position;
    }

    //region Getters and Setters
    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    //endregion
}
