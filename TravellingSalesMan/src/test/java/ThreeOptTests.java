import org.junit.Test;
import org.neu.psa.algorithms.gentic.optimizations.ThreeOpt;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ThreeOptTests {

    @Test
    public void testTsp3opt() {
        int[] tour1 = {0, 1, 2, 3, 4};
        double[][] matrix1 = {{0, 1, 2, 3, 4}, {1, 0, 5, 6, 7}, {2, 5, 0, 8, 9}, {3, 6, 8, 0, 10}, {4, 7, 9, 10, 0}};
        int[] expectedTour1 = {0, 2, 1, 4, 3};
        int[] resultTour1 = ThreeOpt.threeOpt(tour1, matrix1);
        assertArrayEquals(expectedTour1, resultTour1);
    }

    @Test
    public void testTsp3optDistance() {
        int[] tour2 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        double[][] matrix2 = {{0, 2, 3, 4, 5, 6, 7, 8, 9}, {2, 0, 2, 3, 4, 5, 6, 7, 8}, {3, 2, 0, 2, 3, 4, 5, 6, 7}, {4, 3, 2, 0, 2, 3, 4, 5, 6}, {5, 4, 3, 2, 0, 2, 3, 4, 5}, {6, 5, 4, 3, 2, 0, 2, 3, 4}, {7, 6, 5, 4, 3, 2, 0, 2, 3}, {8, 7, 6, 5, 4, 3, 2, 0, 2}, {9, 8, 7, 6, 5, 4, 3, 2, 0}};
        int[] expectedTour2 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        int[] resultTour2 = ThreeOpt.threeOpt(tour2, matrix2);
        assertArrayEquals(expectedTour2, resultTour2);
    }

    @Test
    public void testTsp3Opt2() {
        int[] tour3 = {0, 1, 2, 3};
        double[][] matrix3 = {{0, 1, 2, 3}, {1, 0, 4, 5}, {2, 4, 0, 6}, {3, 5, 6, 0}};
        int[] expectedTour3 = {0, 1, 2, 3};
        int[] resultTour3 = ThreeOpt.threeOpt(tour3, matrix3);
        assertArrayEquals(expectedTour3, resultTour3);
    }

    @Test
    public void testReverse() {
        int[] tour = {0, 1, 2, 3, 4};
        int i = 1;
        int j = 3;
        int[] expected = {0, 3, 2, 1, 4};
        int[] result = ThreeOpt.reverse(tour, i, j);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testTourLength() {
        int[] tour = {0, 1, 2, 3};
        double[][] dist = {
                {0.0, 2.0, 2.5, 3.0},
                {2.0, 0.0, 1.5, 2.0},
                {2.5, 1.5, 0.0, 1.5},
                {3.0, 2.0, 1.5, 0.0}
        };
        int expected = 7;
        int result = ThreeOpt.tourLength(tour, dist);
        assertEquals(expected, result);
    }
}
