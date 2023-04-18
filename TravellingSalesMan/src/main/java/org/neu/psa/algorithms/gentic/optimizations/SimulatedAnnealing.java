/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.neu.psa.algorithms.gentic.optimizations;

import java.util.*;

/**
 *
 * @author varun
 */
public class SimulatedAnnealing {
    private int numNodes; // number of nodes
    private double[][] distanceMatrix; // distance matrix between nodes
    private double initialTemperature; // initial temperature for SA
    private double coolingRate; // cooling rate for SA
    private int[] tour; // tour to be optimized

    // constructor
    public SimulatedAnnealing(int numNodes, double[][] distanceMatrix, double initialTemperature, double coolingRate, int[] tour) {
        this.numNodes = numNodes;
        this.distanceMatrix = distanceMatrix;
        this.initialTemperature = initialTemperature;
        this.coolingRate = coolingRate;
        this.tour = tour;
    }

    // optimize tour using Simulated Annealing
    public int[] optimizeTour() {
        double temperature = initialTemperature;

        // initialize current solution as given tour
        int[] currentTour = Arrays.copyOf(tour, tour.length);
        double currentTourLength = calculateTourLength(currentTour);

        // set best solution to current solution
        int[] bestTour = Arrays.copyOf(currentTour, currentTour.length);
        double bestTourLength = currentTourLength;

        Random rand = new Random();

        // iterate until temperature reaches minimum value
        while (temperature > 1e-8) {
            // generate new candidate solution by swapping two random nodes
            int node1 = rand.nextInt(numNodes);
            int node2 = rand.nextInt(numNodes);
            while (node2 == node1) {
                node2 = rand.nextInt(numNodes);
            }
            int[] newTour = Arrays.copyOf(currentTour, currentTour.length);
            int temp = newTour[node1];
            newTour[node1] = newTour[node2];
            newTour[node2] = temp;
            double newTourLength = calculateTourLength(newTour);

            // accept new solution with a probability based on temperature and energy difference
            double energyDiff = newTourLength - currentTourLength;
            if (energyDiff < 0 || rand.nextDouble() < Math.exp(-energyDiff / temperature)) {
                currentTour = Arrays.copyOf(newTour, newTour.length);
                currentTourLength = newTourLength;
            }

            // update best solution if current solution is better
            if (currentTourLength < bestTourLength) {
                bestTour = Arrays.copyOf(currentTour, currentTour.length);
                bestTourLength = currentTourLength;
            }

            // decrease temperature
            temperature *= coolingRate;
        }

        return bestTour;
    }

    // calculate length of given tour
    public double calculateTourLength(int[] tour) {
        double tourLength = 0.0;
        for (int i = 0; i < numNodes; i++) {
            int j = (i + 1) % numNodes;
            tourLength += distanceMatrix[tour[i]][tour[j]];
        }
        return tourLength;
    }
}
