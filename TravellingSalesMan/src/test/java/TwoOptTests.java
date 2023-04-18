import org.junit.Test;
import org.neu.psa.algorithms.gentic.optimizations.TwoOpt;

import static org.junit.Assert.*;

public class TwoOptTests {

    @Test
    public void testTsp2opt() {
        // Test Case 1
        int[] tour1 = {0, 1, 2, 3, 4};
        double[][] matrix1 = {{0, 1, 2, 3, 4}, {1, 0, 5, 6, 7}, {2, 5, 0, 8, 9}, {3, 6, 8, 0, 10}, {4, 7, 9, 10, 0}};
        int[] expectedTour1 = {0, 2, 1, 3, 4};
        int[] resultTour1 = TwoOpt.tsp2opt(tour1, matrix1);
        assertArrayEquals(expectedTour1, resultTour1);

        // Test Case 2
        int[] tour2 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        double[][] matrix2 = {{0, 2, 3, 4, 5, 6, 7, 8, 9}, {2, 0, 2, 3, 4, 5, 6, 7, 8}, {3, 2, 0, 2, 3, 4, 5, 6, 7}, {4, 3, 2, 0, 2, 3, 4, 5, 6}, {5, 4, 3, 2, 0, 2, 3, 4, 5}, {6, 5, 4, 3, 2, 0, 2, 3, 4}, {7, 6, 5, 4, 3, 2, 0, 2, 3}, {8, 7, 6, 5, 4, 3, 2, 0, 2}, {9, 8, 7, 6, 5, 4, 3, 2, 0}};
        int[] expectedTour2 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        int[] resultTour2 = TwoOpt.tsp2opt(tour2, matrix2);
        assertArrayEquals(expectedTour2, resultTour2);

        // Test Case 3
        int[] tour3 = {0, 1, 2, 3};
        double[][] matrix3 = {{0, 1, 2, 3}, {1, 0, 4, 5}, {2, 4, 0, 6}, {3, 5, 6, 0}};
        int[] expectedTour3 = {0, 1, 2, 3};
        int[] resultTour3 = TwoOpt.tsp2opt(tour3, matrix3);
        assertArrayEquals(expectedTour3, resultTour3);
    }
    public void testTsp2optDistance() {
        // Test Case 1

//        Location[] locations;
//        int[] tour1 = {0, 1, 2, 3, 4};
//        double[][] matrix1 = {{0, 1, 2, 3, 4}, {1, 0, 5, 6, 7}, {2, 5, 0, 8, 9}, {3, 6, 8, 0, 10}, {4, 7, 9, 10, 0}};
//        double expectedDistance1 = 17.0;
//        int[] resultTour = TwoOpt.tsp2opt(tour1, matrix1);
//        double resultDistance1 = utils.findTotalDistance(resultTour, locations);
//        assertEquals(expectedDistance1, resultDistance1, 0.001);
//
//        // Test Case 2
//        int[] tour2 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
//        double[][] matrix2 = {{0, 2, 3, 4, 5, 6, 7, 8, 9}, {2, 0, 2, 3, 4, 5, 6, 7, 8}, {3, 2, 0, 2, 3, 4, 5, 6, 7}, {4, 3, 2, 0, 2, 3, 4, 5, 6}, {5, 4, 3, 2, 0, 2, 3, 4, 5}, {6, 5, 4, 3, 2, 0, 2, 3, 4}, {7, 6, 5, 4, 3, 2, 0, 2, 3}, {8, 7, 6, 5, 4, 3, 2, 0, 2}, {9, 8, 7, 6, 5, 4, 3, 2, 0}};
//        double expectedDistance2 = 44.0;
//        double resultDistance2 = TwoOpt.tsp2opt(tour2, matrix2);
//        assertEquals(expectedDistance2, resultDistance2, 0.001);
//
//        // Test Case 3
//        int[] tour3 = {0, 1, 2, 3};
//        double[][] matrix3 = {{0, 1, 2, 3}, {1, 0, 4, 5}, {2, 4, 0, 6}, {3, 5, 6, 0}};
//        double expectedDistance3 = 12.0;
//        double resultDistance3 = TwoOpt.tsp2opt(tour3, matrix3);
//        assertEquals(expectedDistance3, resultDistance3, 0.001);
    }
}
