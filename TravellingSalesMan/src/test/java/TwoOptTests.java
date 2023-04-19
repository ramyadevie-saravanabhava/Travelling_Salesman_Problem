import org.junit.Assert;
import org.junit.Test;
import org.neu.psa.algorithms.gentic.optimizations.TwoOpt;
import org.neu.psa.christofides.TSP;
import org.neu.psa.model.Location;

import static org.junit.Assert.*;
import static org.neu.psa.algorithms.gentic.optimizations.TwoOpt.totalCost;

public class TwoOptTests {

    @Test
    public void testTsp2opt() {
        int[] tour1 = {0, 1, 2, 3, 4};
        double[][] matrix1 = {{0, 1, 2, 3, 4}, {1, 0, 5, 6, 7}, {2, 5, 0, 8, 9}, {3, 6, 8, 0, 10}, {4, 7, 9, 10, 0}};
        int[] expectedTour1 = {0, 2, 1, 3, 4};
        int[] resultTour1 = TwoOpt.tsp2opt(tour1, matrix1);
        assertArrayEquals(expectedTour1, resultTour1);
    }

    @Test
    public void testTsp2optDistance() {
        int[] tour3 = {0, 1, 2, 3};
        double[][] matrix3 = {{0, 1, 2, 3}, {1, 0, 4, 5}, {2, 4, 0, 6}, {3, 5, 6, 0}};
        int[] expectedTour3 = {0, 1, 2, 3};
        int[] resultTour3 = TwoOpt.tsp2opt(tour3, matrix3);
        assertArrayEquals(expectedTour3, resultTour3);
    }

    @Test
    public void testTsp2opt2(){
        int[] tour2 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        double[][] matrix2 = {{0, 2, 3, 4, 5, 6, 7, 8, 9}, {2, 0, 2, 3, 4, 5, 6, 7, 8}, {3, 2, 0, 2, 3, 4, 5, 6, 7}, {4, 3, 2, 0, 2, 3, 4, 5, 6}, {5, 4, 3, 2, 0, 2, 3, 4, 5}, {6, 5, 4, 3, 2, 0, 2, 3, 4}, {7, 6, 5, 4, 3, 2, 0, 2, 3}, {8, 7, 6, 5, 4, 3, 2, 0, 2}, {9, 8, 7, 6, 5, 4, 3, 2, 0}};
        int[] expectedTour2 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        int[] resultTour2 = TwoOpt.tsp2opt(tour2, matrix2);
        assertArrayEquals(expectedTour2, resultTour2);
    }

    @Test
    public void testTotalCost() {
        int[] cs = {0, 1, 2, 3};
        double[][] table = {
                {0.0, 2.0, 2.5, 3.0},
                {2.0, 0.0, 1.5, 2.0},
                {2.5, 1.5, 0.0, 1.5},
                {3.0, 2.0, 1.5, 0.0}
        };
        double expected = 8.0;
        double result = totalCost(cs, table);
        assertEquals(expected, result, 0.0001);
    }
}
