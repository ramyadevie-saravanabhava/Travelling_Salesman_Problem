import org.junit.Assert;
import org.junit.Test;
import org.neu.psa.christofides.TSP;
import org.neu.psa.model.Location;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.neu.psa.christofides.TSP.*;

public class ChristofidesTests {
    @Test
    public void primsTest() {
        Location[] locations = TSP.readLocations("./crimeTest.csv");
        double[][] distanceMatrix = TSP.calculateDistanceMatrix(locations);

        List<int[]> mst = prim(distanceMatrix, 0);
        List<int[]> expectedMST = new ArrayList<>();
        expectedMST.add(new int[]{0, 1});
        expectedMST.add(new int[]{6, 2});
        expectedMST.add(new int[]{2, 3});
        expectedMST.add(new int[]{1, 4});
        expectedMST.add(new int[]{0, 5});
        expectedMST.add(new int[]{1, 6});
        assertArrayEquals(expectedMST.toArray(), mst.toArray());
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
}
