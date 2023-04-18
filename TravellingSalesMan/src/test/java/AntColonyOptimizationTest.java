import static org.junit.Assert.*;
import org.junit.Test;
import org.neu.psa.algorithms.gentic.optimizations.AntColonyOptimization;

public class AntColonyOptimizationTest {
    @Test
    public void testFindTour() {
        int numNodes = 5;
        int numAnts = 10;
        double alpha = 1.0;
        double beta = 1.0;
        double rho = 0.5;
        double Q = 10.0;
        double[][] distanceMatrix = {
                {0.0, 1.0, 2.0, 3.0, 4.0},
                {1.0, 0.0, 1.0, 2.0, 3.0},
                {2.0, 1.0, 0.0, 1.0, 2.0},
                {3.0, 2.0, 1.0, 0.0, 1.0},
                {4.0, 3.0, 2.0, 1.0, 0.0}
        };
        int[] cTour = {0, 1, 2, 3, 4};
        AntColonyOptimization aco = new AntColonyOptimization(numNodes, numAnts, alpha, beta, rho, Q, distanceMatrix, cTour);
        int[] tour = aco.solve();
        boolean[] visited = new boolean[numNodes];
        for (int i = 0; i < numNodes; i++) {
            visited[i] = false;
        }
        for (int i = 0; i < numNodes; i++) {
            visited[tour[i]] = true;
        }
        for (int i = 0; i < numNodes; i++) {
            assertTrue(visited[i]);
        }
    }
    @Test
    public void testCalculateTourLength() {
        int numNodes = 4;
        double[][] distanceMatrix = {
                {0, 2, 5, 7},
                {2, 0, 6, 9},
                {5, 6, 0, 3},
                {7, 9, 3, 0}
        };
        int[] tour = {0, 2, 3, 1};
        AntColonyOptimization aco = new AntColonyOptimization(numNodes, 10, 1, 2, 0.5, 1, distanceMatrix, null);
        double tourLength = aco.calculateTourLength(tour);
        assertEquals(19.0, tourLength, 0.01);
    }

    @Test
    public void testInitializePheromoneMatrix() {
        int numNodes = 5;
        double[][] distanceMatrix = new double[numNodes][numNodes];
        int[] cTour = {0, 1, 2, 3, 4};
        AntColonyOptimization aco = new AntColonyOptimization(numNodes, 10, 1, 2, 0.5, 1, distanceMatrix, cTour);
        double[][] pheromoneMatrix = aco.getPheromoneMatrix();
        double initialPheromone = 1.0 / numNodes;
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                assertEquals(initialPheromone, pheromoneMatrix[i][j], 0.01);
            }
        }
    }

}