package org.neu.psa.algorithms.gentic.optimizations;
public class ThreeOpt {
    // 3-Opt algorithm for TSP
    public static int[] threeOpt(int[] tour, double[][] dist) {
        int n = tour.length;
        boolean improve = true;
        while (improve) {
            improve = false;
            for (int i = 0; i < n - 2; i++) {
                for (int j = i + 2; j < n-1; j++) {
                    for (int k = j + 2; k < n; k++) {
                        int[] newTour = reverse(tour, i+1, j);
                        newTour = reverse(newTour, j+1, k);
                        newTour = reverse(newTour, k+1, n-1);
                        if (tourLength(newTour, dist) < tourLength(tour, dist)) {
                            tour = newTour;
                            improve = true;
                        }
                    }
                }
            }
        }
        return tour;
    }

    // Reverse a section of a tour
    private static int[] reverse(int[] tour, int i, int j) {
        int[] newTour = new int[tour.length];
        for (int k = 0; k <= i - 1; k++) {
            newTour[k] = tour[k];
        }
        int dec = 0;
        for (int k = i; k <= j; k++) {
            newTour[k] = tour[j - dec];
            dec++;
        }
        for (int k = j + 1; k < tour.length; k++) {
            newTour[k] = tour[k];
        }
        return newTour;
    }

    // Calculate the length of a tour
    private static int tourLength(int[] tour, double[][] dist) {
        int length = 0;
        for (int i = 0; i < tour.length; i++) {
            int from = tour[i];
            int to = tour[(i + 1) % tour.length];
            length += dist[from][to];
        }
        return length;
    }
}
