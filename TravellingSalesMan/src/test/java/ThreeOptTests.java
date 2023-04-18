import org.junit.Test;
import org.neu.psa.algorithms.gentic.optimizations.ThreeOpt;

import static org.junit.Assert.assertArrayEquals;

public class ThreeOptTests {

    @Test
    public void testTsp3opt() {
        int[] tour1 = {0, 1, 2, 3, 4};
        double[][] matrix1 = {{0, 1, 2, 3, 4}, {1, 0, 5, 6, 7}, {2, 5, 0, 8, 9}, {3, 6, 8, 0, 10}, {4, 7, 9, 10, 0}};
        int[] expectedTour1 = {0, 2, 1, 3, 4};
        int[] resultTour1 = ThreeOpt.threeOpt(tour1, matrix1);
        assertArrayEquals(expectedTour1, resultTour1);

        int[] tour2 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        double[][] matrix2 = {{0, 2, 3, 4, 5, 6, 7, 8, 9}, {2, 0, 2, 3, 4, 5, 6, 7, 8}, {3, 2, 0, 2, 3, 4, 5, 6, 7}, {4, 3, 2, 0, 2, 3, 4, 5, 6}, {5, 4, 3, 2, 0, 2, 3, 4, 5}, {6, 5, 4, 3, 2, 0, 2, 3, 4}, {7, 6, 5, 4, 3, 2, 0, 2, 3}, {8, 7, 6, 5, 4, 3, 2, 0, 2}, {9, 8, 7, 6, 5, 4, 3, 2, 0}};
        int[] expectedTour2 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        int[] resultTour2 = ThreeOpt.threeOpt(tour2, matrix2);
        assertArrayEquals(expectedTour2, resultTour2);

        int[] tour3 = {0, 1, 2, 3};
        double[][] matrix3 = {{0, 1, 2, 3}, {1, 0, 4, 5}, {2, 4, 0, 6}, {3, 5, 6, 0}};
        int[] expectedTour3 = {0, 1, 2, 3};
        int[] resultTour3 = ThreeOpt.threeOpt(tour3, matrix3);
        assertArrayEquals(expectedTour3, resultTour3);
    }
    public void testTsp2optDistance() {
    }
}
