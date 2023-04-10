/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.neu.psa.model;

/**
 *
 * @author varun
 */
public class Edge {
    
    Location src;
    Location dest;
    double distance;

    public Edge(Location src, Location dist) {
        this.src = src;
        this.dest = dist;
        this.distance = src.distanceTo(dist);
    }
}
