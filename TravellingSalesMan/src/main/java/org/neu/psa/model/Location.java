package org.neu.psa.model;

public class Location {
    float latitude;
    float longitude;
    int id;
    String name;

    public Location() {
        latitude = 0.0F;
        longitude = 0.0F;
        id = 0;
        name = "";
    }

    public Location(float latitude, float longitude, int id, String name) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "\nCity Name : " + this.name +
                "\nID : " + this.id +
                "\nLatitude : " + this.latitude +
                "\nLongitude : " + this.longitude;
    }
}
