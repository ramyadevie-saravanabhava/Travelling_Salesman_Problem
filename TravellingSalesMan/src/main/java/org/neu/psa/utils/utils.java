package org.neu.psa.utils;

import org.neu.psa.model.Edge;
import org.neu.psa.model.Location;

import java.util.ArrayList;
import java.util.List;

public class utils {

    public static String getDataFilePath() {
        return "./crimeSample.csv";
    }

    public static ArrayList<Location> readLocations() {
        ArrayList<Location> allLocations = new ArrayList<>();
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

    public static double findTotalDistance(List<Integer> ids, Location[] locations) {
        double distance = 0;
        Location src;
        Location dest;
        for(int i = 0; i < locations.length-1; i++) {
            src = Location.findLocationById(ids.get(i), locations);
            dest = Location.findLocationById(ids.get(i+1), locations);
            distance += src.distanceTo(dest);
        }
        // Need this to connect the start and end of the TSP graph.
        src = Location.findLocationById(ids.get(0), locations);
        dest = Location.findLocationById(ids.get(locations.length-1), locations);
        distance += src.distanceTo(dest);

        return distance;
    }
}
