import org.junit.Assert;
import org.junit.Test;
import org.neu.psa.christofides.TSP;
import org.neu.psa.model.Location;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.neu.psa.christofides.Edge;
import static org.neu.psa.christofides.TSP.*;

public class ChristofidesTests {
    
     @Test
    public void testGetMST() {
        double[][] graph = {
            {0.0, 2.0, 0.0, 6.0, 0.0},
            {2.0, 0.0, 3.0, 8.0, 5.0},
            {0.0, 3.0, 0.0, 0.0, 7.0},
            {6.0, 8.0, 0.0, 0.0, 9.0},
            {0.0, 5.0, 7.0, 9.0, 0.0}
        };
        Edge[] expectedMST = {
            new Edge(0, 1, 2.0),
            new Edge(1, 2, 3.0),
            new Edge(0, 3, 6.0),
            new Edge(1, 4, 5.0)
        };
        Edge[] actualMST = GetMST(graph);
        assertArrayEquals(expectedMST, actualMST);
    }
    
    @Test
    public void testMinKey() {
        Location[] locations = TSP.readLocations("./crimeTest.csv");
        double[][] distanceMatrix = TSP.calculateDistanceMatrix(locations);
        int[] key = {2, 1, 3, 4, 6, 7, 5};
        Boolean[] mstSet = {true, true, false, false, true, true, false};
        int expected = 2;
        int result = TSP.minKey(key, mstSet);
        Assert.assertEquals(result, expected);
    }
    
    
     @Test
    public void testFindEulerianTour() {
        List<int[]> matchedMST = new ArrayList<>();
        matchedMST.add(new int[]{0, 1});
        matchedMST.add(new int[]{1, 2});
        matchedMST.add(new int[]{2, 3});
        matchedMST.add(new int[]{3, 4});
        matchedMST.add(new int[]{4, 0});

        double[][] graph = {
                {0.0, 2.0, 3.0, 0.0, 5.0},
                {2.0, 0.0, 4.0, 0.0, 0.0},
                {3.0, 4.0, 0.0, 6.0, 0.0},
                {0.0, 0.0, 6.0, 0.0, 1.0},
                {5.0, 0.0, 0.0, 1.0, 0.0}
        };

        List<Integer> expectedEulerianPath = new ArrayList<>();
        expectedEulerianPath.add(1);
        expectedEulerianPath.add(0);
        expectedEulerianPath.add(2);
        expectedEulerianPath.add(1);
        expectedEulerianPath.add(3);
        expectedEulerianPath.add(2);
        expectedEulerianPath.add(4);
        expectedEulerianPath.add(0);

        List<Integer> actualEulerianPath = findEulerianTour(matchedMST, graph);

        assertEquals(expectedEulerianPath, actualEulerianPath);
    }
}
