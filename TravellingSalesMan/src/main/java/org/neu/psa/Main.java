package org.neu.psa;

import org.neu.psa.model.Location;
import org.neu.psa.utils.utils;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello !");
        ArrayList<Location> allLoc = utils.readLocations();
        double dist = utils.findDistance(allLoc.get(0), allLoc.get(1));
        System.out.println("Miles " + dist);
    }
}