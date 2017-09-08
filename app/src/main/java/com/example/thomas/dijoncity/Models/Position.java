package com.example.thomas.dijoncity.Models;

/**
 * Created by Thomas on 08/09/2017.
 */

public class Position {

    private double lat;
    private double lon;

    public Position(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    //region Getters and Setters
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
    //endregion
}
