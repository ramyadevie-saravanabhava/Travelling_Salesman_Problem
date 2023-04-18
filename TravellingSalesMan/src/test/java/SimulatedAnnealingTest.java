import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.neu.psa.algorithms.gentic.optimizations.SimulatedAnnealing;

public class SimulatedAnnealingTest {
    private int numNodes;
    private double coolingRate;
    private int[] tour;
    private double initialTemperature;
    private double[][] distanceMatrix;


    @Before
    public void setUp() {
        numNodes = 5;
        initialTemperature = 100.0;
        coolingRate = 0.99;
        tour = new int[]{0, 1, 2, 3, 4};
        distanceMatrix = new double[][]{{0, 2, 9, 10, 5}, {2, 0, 6, 4, 8}, {9, 6, 0, 3, 7}, {10, 4, 3, 0, 2}, {5, 8, 7, 2, 0}};
    }

    @Test
    public void testOptimizeTour() {
        SimulatedAnnealing sa = new SimulatedAnnealing(numNodes, distanceMatrix, initialTemperature, coolingRate, tour);
        int[] optimizedTour = sa.optimizeTour();
        double optimizedTourLength = sa.calculateTourLength(optimizedTour);

        assertEquals(18.0, optimizedTourLength, 0.001);
    }

    @Test
    public void testCalculateTourLength() {
        SimulatedAnnealing sa = new SimulatedAnnealing(numNodes, distanceMatrix, initialTemperature, coolingRate, tour);
        double tourLength = sa.calculateTourLength(tour);

        assertEquals(18.0, tourLength, 0.001);
    }

    @Test
    public void testOptimizeTourWithSameNodes() {
        // test that optimized tour is the same when all nodes are the same
        int[] sameNodesTour = new int[]{0, 0, 0, 0, 0};
        SimulatedAnnealing sa = new SimulatedAnnealing(numNodes, distanceMatrix, initialTemperature, coolingRate, sameNodesTour);
        int[] optimizedTour = sa.optimizeTour();

        assertArrayEquals(sameNodesTour, optimizedTour);
    }

    @Test
    public void testSimulatedAnnealingWithNonSymmetricDistanceMatrix() {
        double[][] distanceMatrix = {{0.0, 1.0}, {2.0, 0.0}};
        SimulatedAnnealing sa = new SimulatedAnnealing(2, distanceMatrix, 100.0, 0.95, new int[] {0, 1});
        int[] optimizedTour = sa.optimizeTour();

        assertNotEquals(optimizedTour, new int[] {0, 1});
    }

    @Test
    public void testSimulatedAnnealingWithZeroInitialTemperature() {
        double[][] distanceMatrix = {{0.0, 1.0}, {1.0, 0.0}};
        SimulatedAnnealing sa = new SimulatedAnnealing(2, distanceMatrix, 0.0, 0.95, new int[] {0, 1});
        int[] optimizedTour = sa.optimizeTour();

        assertNotEquals(optimizedTour, new int[] {0, 1});
    }
}