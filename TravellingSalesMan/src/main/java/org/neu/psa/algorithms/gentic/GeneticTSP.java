package org.neu.psa.algorithms.gentic;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.neu.psa.model.Location;

public class GeneticTSP{

    public static double [][] adjacencyMatrix;
    public static Location [] Locations;

    public static void main(String[] args) throws IOException {
        double start = System.currentTimeMillis();
        BufferedReader reader = new BufferedReader(new FileReader("/Users/ramya/Documents/NEU/PSA/INFO-6205-Spring-2023/TravellingSalesMan/crimeSample.csv"));
        List<String> lines = new ArrayList<>();
        String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        Locations = new Location[lines.size()];
            for(int i=0; i<Locations.length; i++){
                String[]parts = lines.get(i).split(",");
                Locations[i] = new Location (i+1, parts[0], Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
            }
        adjacencyMatrix = new double[Locations.length+1][Locations.length+1];
            for(int i=1; i<=Locations.length;i++){
                for(int j=1; j<=Locations.length;j++){
                    adjacencyMatrix[i][j] = Locations[i-1].distanceTo(Locations[j-1]);
                }
            }
        int runs = 1;
        Path [] bests = new Path[runs];
        for(int i = 0; i<runs; i++){
            Population population = new Population(adjacencyMatrix, Locations, 0.05, 2000);
            while(population.since_change < 100){
                population.naturalSelection();
                population.generate();
                population.calcFitness();
            }
            bests[i] = population.all_time;
        }
        Path best = bests[0];
        System.out.println("\nbests [0]: "+best.distance);
        for(int i=1; i<runs; i++){
            System.out.println("bests ["+i+"]: "+bests[i].distance);
            if(bests[i].distance < best.distance){
                best = bests[i];
            }
        }
        System.out.println("\ntotal best: \n"+best.makeString()+"\nwith a distance of: "+best.distance);
        double secs_taken = (System.currentTimeMillis() - start)/1000;
        System.out.println("\nfinished in "+(int)(secs_taken/60)+" mins "+(int)(secs_taken%60)+" secs");
    }
}