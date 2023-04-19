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
    public void testFindOddVertexes(){
        Location[] locations = TSP.readLocations("./crimeTest.csv");
        List<int[]> MST = new ArrayList<>();
        MST.add(new int[]{0, 5});
        MST.add(new int[]{0, 1});
        MST.add(new int[]{1, 4});
        MST.add(new int[]{1, 6});
        MST.add(new int[]{2, 6});
        MST.add(new int[]{2, 3});
        ArrayList expectedVertexes = new ArrayList<>();
        expectedVertexes.add(1);
        expectedVertexes.add(3);
        expectedVertexes.add(4);
        expectedVertexes.add(5);

        assertArrayEquals(expectedVertexes.toArray(), TSP.findOddVertexes(MST).toArray());
    }

    @Test
    public void testFindEulerianTour() {
        Location[] locations = TSP.readLocations("./crimeTest.csv");
        double[][] distanceMatrix = TSP.calculateDistanceMatrix(locations);
        List<int[]> MST = new ArrayList<>();
        MST.add(new int[]{0, 5});
        MST.add(new int[]{0, 1});
        MST.add(new int[]{1, 4});
        MST.add(new int[]{1, 6});
        MST.add(new int[]{2, 6});
        MST.add(new int[]{2, 3});
        MST.add(new int[]{4, 1, 5648});
        MST.add(new int[]{3, 5, 29789});
        ArrayList expectedPath = new ArrayList<>();
        expectedPath.add(5);
        expectedPath.add(3);
        expectedPath.add(2);
        expectedPath.add(6);
        expectedPath.add(1);
        expectedPath.add(4);
        expectedPath.add(4);
        expectedPath.add(0);
        expectedPath.add(0);
        List<Integer> tour = TSP.findEulerianTour(MST,distanceMatrix);

        assertArrayEquals(expectedPath.toArray(), tour.toArray());
    }
}
