package org.neu.psa.utils;

import org.neu.psa.model.Location;

import java.util.ArrayList;

public class utils {
    public static ArrayList<Location> readLocations() {
        ArrayList<Location> allLocations = new ArrayList<>();
        Location loc1 = new Location(51.515192,-0.016542,  1, "Cambridgeshire Street");
        Location loc2 = new Location(51.543142,-0.135545,  2, "Derbyshire street");
        Location loc3 = new Location(51.503993, 0.045955, 3, "Liverpool");
        Location loc4 = new Location(51.545753, -0.477183, 4, "Bristol");
        allLocations.add(loc1);
        allLocations.add(loc2);
        allLocations.add(loc3);
        allLocations.add(loc4);
        return allLocations;
    }

    public static double findDistance(Location start, Location destination) {
        double startLat = start.getLatitude();
        double startLong = start.getLongitude();
        double destLat = destination.getLatitude();
        double destLong = destination.getLongitude();

        double theta = startLong - destLong;
        double dist = Math.sin(Math.toRadians(startLat)) * Math.sin(Math.toRadians(destLat)) + Math.cos(Math.toRadians(startLat)) * Math.cos(Math.toRadians(destLat)) * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }
}
