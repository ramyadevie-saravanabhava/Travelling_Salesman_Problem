package org.neu.psa.algorithms.gentic.optimizations;

import java.util.Arrays;
public class TwoOpt {
    private static int[] reverse(int[] arr ) {
        int n = arr.length;
        int[] res = new int[n];
        for (int k : arr) res[--n] = k;

        return res;
    }
    private static int[] optSwap(int[] cs,int i, int k) {
        int[] res = new int[cs.length];
        System.arraycopy(cs,0, res, 0,i+1);
        int []rev = reverse(Arrays.copyOfRange(cs,i,k + 1));
        System.arraycopy(rev,0,res, i, rev.length);
        System.arraycopy(cs,k + 1, res,k + 1,cs.length - k-1);

        return res;
    }
    private static double totalCost(int[]  cs, double[][]table) {
        int n = table.length;
        double z = table[cs[0]][cs[n-1]];
        for (int k = 0; k < n-1; k++) z += table[cs[k]][cs[k+1]];

        return z;
    }

    public static int[] tsp2opt(int[] cs, double[][] table) {
        int[] bestTour = new int[cs.length];
        double bestCost = 0;
        boolean newSearch = false;
        System.arraycopy(cs,0,bestTour,0,cs.length);

        for(int maxCount = cs.length*25; maxCount > 0 ; maxCount--)
        {
            bestCost = totalCost(bestTour,table);
            for(int i = 1; i < cs.length-1; i++){
                for(int k = i+1; k <cs.length - 1;k++){
                    int[] newTour = optSwap(bestTour,i,k);

                    if(bestCost > totalCost(newTour,table))
                    {
                        if(cs.length == 40) System.out.println("True");
                        System.arraycopy(newTour,0,bestTour,0,newTour.length);
                        if(cs.length == 40)System.out.println(Arrays.toString(bestTour));
                        newSearch = true;
                        break;
                    }
                }
                if(newSearch)
                    break;
            }
        }

        return bestTour;
    }
}
