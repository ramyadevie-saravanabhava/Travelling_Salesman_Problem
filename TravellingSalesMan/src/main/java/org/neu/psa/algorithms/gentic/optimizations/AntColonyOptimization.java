/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.neu.psa.algorithms.gentic.optimizations;

/**
 *
 * @author varun
 */

import java.util.Arrays;
import java.util.Random;
import java.util.*;




public class AntColonyOptimization {
    private final int numNodes;
    private final int numAnts;
    private final double alpha;
    private final double beta;
    private final double rho;
    private final double Q;
    private final double[][] distanceMatrix;

    private double[][] pheromoneMatrix;
    private final Random random;
    private final int[] cTour; 

    public AntColonyOptimization(int numNodes, int numAnts, double alpha, double beta, double rho, double Q, double[][] distanceMatrix, int[] cTour) {
        this.numNodes = numNodes;
        this.numAnts = numAnts;
        this.alpha = alpha;
        this.beta = beta;
        this.rho = rho;
        this.Q = Q;
        this.distanceMatrix = distanceMatrix;
        this.pheromoneMatrix = new double[numNodes][numNodes];
        this.random = new Random();
        this.cTour = cTour;
        initializePheromoneMatrix();
    }

    public double[][] getPheromoneMatrix() {
        return pheromoneMatrix;
    }

    private void initializePheromoneMatrix() {
        double initialPheromone = 1.0 / numNodes;
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                pheromoneMatrix[i][j] = initialPheromone;
            }
        }
    }

    public int[] solve() {
        int[] bestTour = null;
        double bestTourLength = Double.MAX_VALUE;
        int[] tour = null;
        for (int i = 0; i < numAnts; i++) {
            if(tour == null){
                tour = cTour;
            }else{
                tour = constructTour();
            }
            double tourLength = calculateTourLength(tour);
            if (tourLength < bestTourLength) {
                bestTour = tour;
                bestTourLength = tourLength;
            }
            updatePheromoneMatrix(tour, tourLength);
        }
        return bestTour;
    }

    private int[] constructTour() {
        int startNode = cTour[0];
        int[] tour = new int[numNodes];
        tour[0] = startNode;
        boolean[] visited = new boolean[numNodes];
        visited[startNode] = true;
        for (int i = 1; i < numNodes; i++) {
            int nextNode = selectNextNode(tour, visited);
            tour[i] = nextNode;
            visited[nextNode] = true;
        }
        return tour;
    }

    private int selectNextNode(int[] tour, boolean[] visited) {
        int currentNode = tour[tour.length - 1];
        double[] probabilities = new double[numNodes];
        double totalProbability = 0.0;
        for (int i = 0; i < numNodes; i++) {
            if (!visited[i]) {
                probabilities[i] = Math.pow(pheromoneMatrix[currentNode][i], alpha) *
                        Math.pow(1.0 / distanceMatrix[currentNode][i], beta);
                totalProbability += probabilities[i];
            }
        }
        double r = random.nextDouble() * totalProbability;
//        double r = random.nextDouble();
        double sum = 0.0;
        for (int i = 0; i < numNodes; i++) {
            if (!visited[i]) {
                sum += probabilities[i];
                if (sum >= r) {
                    return i;
                }
            }
        }
        return -1;
    }

    public double calculateTourLength(int[] tour) {
        double tourLength = 0.0;
        for (int i = 0; i<tour.length;i++){
                if (i < numNodes - 1) {
            tourLength += distanceMatrix[tour[i]][tour[i + 1]];
        } else {
            tourLength += distanceMatrix[tour[i]][tour[0]];
        }
    }
    return tourLength;
}

private void updatePheromoneMatrix(int[] tour, double tourLength) {
    double pheromoneDeposit = Q / tourLength;
    for (int i = 0; i < numNodes; i++) {
        int fromNode = tour[i];
        int toNode = tour[(i + 1) % numNodes];
        pheromoneMatrix[fromNode][toNode] = (1.0 - rho) * pheromoneMatrix[fromNode][toNode] + rho * pheromoneDeposit;
        pheromoneMatrix[toNode][fromNode] = (1.0 - rho) * pheromoneMatrix[toNode][fromNode] + rho * pheromoneDeposit;
    }
}
        }



