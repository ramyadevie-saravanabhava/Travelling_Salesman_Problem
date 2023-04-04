package org.neu.psa.model;

import org.neu.psa.utils.utils;

public class Location {
    public double latitude;
    public double longitude;
    public int id;
    public String name;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Location() {
        latitude = 0.0;
        longitude = 0.0;
        id = 0;
        name = "";
    }

    public Location( int id, String name, double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
        this.name = name;
    }
//    public Location( double longitude, double latitude, int id, String name) {
//        this.latitude = latitude;
//        this.longitude = longitude;
//        this.id = id;
//        this.name = name;
//    }
    public double distanceTo(Location to){

        if(this == to){
            return 0;//same town
        }

        //radius of the earth = 6371km
        double distance = utils.findDistance(this, to);
        return distance;
    }
    @Override
    public String toString() {
        return "\nCity Name : " + this.name +
                "\nID : " + this.id +
                "\nLatitude : " + this.latitude +
                "\nLongitude : " + this.longitude;
    }
}
