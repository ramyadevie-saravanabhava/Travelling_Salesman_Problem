/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.neu.psa.christofides;

/**
 *
 * @author varun
 */
import java.io.*;
import java.util.*;
import org.neu.psa.algorithms.gentic.optimizations.AntColonyOptimization;
import org.neu.psa.algorithms.gentic.optimizations.SimulatedAnnealing;

import org.neu.psa.algorithms.gentic.optimizations.ThreeOpt;
import org.neu.psa.algorithms.gentic.optimizations.TwoOpt;
import org.neu.psa.model.Location;
import org.neu.psa.utils.utils;

public class TSP {
    public static Location[] locations;

    public static Location[] readLocations(String filePath){
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            List<String> lines = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                lines.add(line);
            }

            locations = new Location[lines.size()];
            for(int i = 0; i< locations.length; i++){
                String[]parts = lines.get(i).split(",");
                locations[i] = new Location (i, parts[0].substring(parts[0].length() - 5),Double.parseDouble(parts[2]), Double.parseDouble(parts[1]) );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  locations;
    }

    public static double[][] calculateDistanceMatrix(Location[] locations) {
        int n = locations.length;
        double[][] distanceMatrix = new double[n][n];

        for (int i = 0; i < n-1; i++) {
            for (int j = i + 1; j < n; j++) {
                double distance = locations[i].distanceTo(locations[j]);
                distanceMatrix[i][j] = distance;
                distanceMatrix[j][i] = distance;
            }
        }
        
           return distanceMatrix;
    }

    public static void main(String[] args) {
        locations = readLocations(utils.getDataFilePath());
        double[][] distanceMatrix = calculateDistanceMatrix(locations);
        double sum = 0.0;

        long startTimeChristofides = System.currentTimeMillis();
        Edge[] mst = GetMST(distanceMatrix);
        List<int[]> mstList = new ArrayList<>();
        System.out.println("Vertices: " + mst.length);
        for (Edge e : mst) {
        //    System.out.println(e.either() + "-" + e.other(e.either()) + " " + e.weight);
            sum += e.weight;
            int[] nodePair = { e.either(), e.other(e.either()) };
                mstList.add(nodePair);
        }

        //System.out.println("MST " + mstList);
        System.out.println("MST Distance: " + sum);
        List<Integer> oddVertices = findOddVertexes(mstList);
        minimumWeightMatching(mstList, distanceMatrix, oddVertices);

        List<Integer> eulerianTour = findEulerianTour(mstList, distanceMatrix);


        int current = eulerianTour.get(0);
        List<Integer> path = new ArrayList<>();
        List<String> pathHash = new ArrayList<>();
        path.add(current);
        boolean[] visited = new boolean[eulerianTour.size()];
        visited[eulerianTour.get(0)] = true;
        double length = 0;

        for (int v : eulerianTour) {
            if (!visited[v]) {
                path.add(v);                
                visited[v] = true;
                length += distanceMatrix[current][v];
                current = v;
            }
        }
        long endTimeChristofides = System.currentTimeMillis();

//        long endTimeChristofides = System.currentTimeMillis();
//        long elapsedTime = endTimeChristofides - startTimeChristofides;
//        double elapsedTimeSeconds = (double) elapsedTime / 1000.0;
//        System.out.println("### CHRISTOFIDES Elapsed time: " + elapsedTimeSeconds + " seconds");

        length += distanceMatrix[current][eulerianTour.get(0)];
    
        path.add(eulerianTour.get(0));
            
        for(int i = 0 ; i < path.size(); i++ ){
            pathHash.add(Location.findLocationById(path.get(i), locations).name);
        }

        int[] pathArr = path.stream().mapToInt(Integer::intValue).toArray();
        long startTime2OPT = System.currentTimeMillis();
        int[] twoOptArr = TwoOpt.tsp2opt(pathArr, distanceMatrix);
        long endTime2OPT = System.currentTimeMillis();

        long startTime3OPT = System.currentTimeMillis();
        int[] threeOptArr = ThreeOpt.threeOpt(pathArr, distanceMatrix);
        long endTime3OPT = System.currentTimeMillis();

        List<Integer> threeOptList = new ArrayList<>();
        List<String> threeOptNameHash = new ArrayList<>();
        List<Integer> twoOptList = new ArrayList<>();
        List<String> twoOptNameHash = new ArrayList<>();

        for (int i : twoOptArr) {
            twoOptList.add(i);
            twoOptNameHash.add(Location.findLocationById(i, locations).name);
        }

        for (int i : threeOptArr) {
            threeOptList.add(i);
            threeOptNameHash.add(Location.findLocationById(i, locations).name);
        }
        
        long elapsedTime = endTimeChristofides - startTimeChristofides;
        double elapsedTimeSeconds = (double) elapsedTime / 1000.0;
        System.out.println("### CHRISTOFIDES Elapsed time: " + elapsedTimeSeconds + " seconds");
        System.out.println("Christofides Result Eulerian path: " + path );
        System.out.println("Christofides Result Hash: " + pathHash);
        System.out.println(" Christofides Result length of the path: " + length + " meters");
        System.out.println("-------------------------------------");
        

        
        elapsedTime = endTime2OPT - startTime2OPT;
        elapsedTimeSeconds = (double) elapsedTime / 1000.0;
        System.out.println("#### 2OPT Elapsed time: " + elapsedTimeSeconds + " seconds");
        System.out.println("Two OPT Route : " + twoOptList);
        System.out.println("Two OPT Route Hash : " + twoOptNameHash);
        System.out.println("Two OPT Optimized Tour Length : " + utils.findTotalDistance(twoOptList, locations) + " meters");
        System.out.println("-------------------------------------");
        
        
        
        elapsedTime = endTime3OPT - startTime3OPT;
        elapsedTimeSeconds = (double) elapsedTime / 1000.0;
        System.out.println("#### 3 OPT Elapsed time: " + elapsedTimeSeconds + " seconds");
        System.out.println("Three OPT Route : " + threeOptList);
        System.out.println("Three OPT Route Hash : " + threeOptNameHash);
        System.out.println("Three OPT Optimized Tour Length : " + utils.findTotalDistance(threeOptList, locations)+ " meters");
        System.out.println("-------------------------------------");
        
      
        List<Integer> acoList = new ArrayList<>();

        AntColonyOptimization aco = new AntColonyOptimization(locations.length, 1000, 1, 5, 0.5, 500, distanceMatrix,threeOptArr );
        long startTimeACO = System.currentTimeMillis();
        int[] tour = aco.solve();
        long endTimeACO = System.currentTimeMillis();
        elapsedTime = endTimeACO - startTimeACO;
        elapsedTimeSeconds = (double) elapsedTime / 1000.0;
        List<String> acoHash = new ArrayList<>();
        for (int i : tour) {
            acoList.add(i);
            acoHash.add(Location.findLocationById(i, locations).name);
        }
        System.out.println("### ACO Elapsed time: " + elapsedTimeSeconds + " seconds");
        System.out.println("Ant Colony Optimized Tour: " + Arrays.toString(tour));
        System.out.println("Ant Colony Route Hash : " + acoHash);
        System.out.println("Ant Colony Optimized Tour Length: " + utils.findTotalDistance(acoList, locations));



        System.out.println("-------------------------------------");

        List<Integer> saList = new ArrayList<>();
        SimulatedAnnealing sa = new SimulatedAnnealing(locations.length, distanceMatrix, 10000, 0.05, threeOptArr);

        long startTimeSA = System.currentTimeMillis();
        int[] saTour = sa.optimizeTour();
        long endTimeSA = System.currentTimeMillis();

        elapsedTime = endTimeSA - startTimeSA;
        elapsedTimeSeconds = (double) elapsedTime / 1000.0;
        System.out.println("### Simulated Annealing Elapsed time: " + elapsedTimeSeconds + " seconds");
        List<String> saHash = new ArrayList<>();
        for (int i : saTour) {
            saList.add(i);
            saHash.add(Location.findLocationById(i, locations).name);
        }
        System.out.println("Simulated Annealing Tour: " + Arrays.toString(saTour));
        System.out.println("Simulated Annealing Route Hash : " + saHash);
        System.out.println("Simulated Annealing Optimized Tour Length: " + utils.findTotalDistance(saList, locations)+ " meters");
    }


    public static int minKey(int key[], Boolean mstSet[]) {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;
 
        for (int v = 0; v < locations.length; v++)
            if (mstSet[v] == false && key[v] < min) {
                min = key[v];
                min_index = v;
            }
        return min_index;
    }

    static List<int[]> minWeightMatching(double[][] graph, List<Integer> vertices) {
        int n = vertices.size();
        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int u = vertices.get(i);
            double minWeight = Double.POSITIVE_INFINITY;
            int minVertex = -1;
            for (int j = 0; j < n; j++) {
                int v = vertices.get(j);
                if (u != v && graph[u][v] < minWeight) {
                    minWeight = graph[u][v];
                    minVertex = v;
                }
            }
            edges.add(new int[]{u, minVertex});
        }
        return maxWeightMatching(graph, edges);
    }

    static List<Integer> eulerianCircuit(double[][] graph) {
        int n = graph.length;
        List<Integer> circuit = new ArrayList<>();
        int start = 0;
        for (int i = 0; i < n; i++) {
            int degree = 0;
            for (int j = 0; j < n; j++) {
                if (graph[i][j] > 0) {
                    degree++;
                }
            }
            if (degree % 2 == 1) {
                start = i;
                break;
            }
        }
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(start);
        while (!stack.isEmpty()) {
            int u = stack.peek();
            for (int v = 0; v < n; v++) {
                if (graph[u][v] > 0) {
                    stack.push(v);
                    graph[u][v] = graph[v][u] = 0;
                    break;
                }
            }
            if (stack.peek() == u) {
                stack.pop();
                circuit.add(u);
            }
        }
        return circuit;
    }



    static List<int[]> maxWeightMatching(double[][] graph, List<int[]> edges) {
        int n = graph.length;
        int[] mate = new int[n];
        Arrays.fill(mate, -1);
        double[] key = new double[n];
        Arrays.fill(key, Double.NEGATIVE_INFINITY);
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            double weight = graph[u][v];
            if (weight > key[u]) {
                key[u] = weight;
                mate[u] = v;
            }
            if (weight > key[v]) {
                key[v] = weight;
                mate[v] = u;
            }
        }
        boolean[] visited = new boolean[n];
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingDouble(i -> -key[i]));
        for (int i = 0; i < n; i++) {
            if (mate[i] == -1) {
                pq.offer(i);
                visited[i] = true;
            }
        }
        while (!pq.isEmpty()) {
            int u = pq.poll();
            visited[u] = true;
            for (int v = 0; v < n; v++) {
                if (!visited[v] && graph[u][v] > key[v]) {
                    pq.remove(v);
                    key[v] = graph[u][v];
                    mate[v] = u;
                    pq.offer(v);
                }
            }
        }
        List<int[]> matching = new ArrayList<>();
        for (int u = 0; u < n; u++) {
            if (mate[u] != -1) {
                matching.add(new int[]{u, mate[u]});
            }
        }
        return matching;
    }
    
    public static List<Integer> findOddVertexes(List<int[]> MST) {
        Map<Integer, Integer> tmp_g = new HashMap<>();
        List<Integer> vertexes = new ArrayList<>();

        for (int[] edge : MST) {
            int v1 = edge[0];
            int v2 = edge[1];

            if (!tmp_g.containsKey(v1)) {
                tmp_g.put(v1, 0);
            }

            if (!tmp_g.containsKey(v2)) {
                tmp_g.put(v2, 0);
            }

            tmp_g.put(v1, tmp_g.get(v1) + 1);
            tmp_g.put(v2, tmp_g.get(v2) + 1);
        }

        for (int vertex : tmp_g.keySet()) {
            if (tmp_g.get(vertex) % 2 == 1) {
                vertexes.add(vertex);
            }
        }

        return vertexes;
    }
 
  public static void minimumWeightMatching(List<int[]> mst, double[][] graph, List<Integer> oddVert) {
        Collections.shuffle(oddVert);
        
        while (!oddVert.isEmpty()) {
            int v = oddVert.remove(oddVert.size() - 1);
            double length = Double.POSITIVE_INFINITY;
            int u = 0;
            int closest = 0;
            
            for (int i = 0; i < oddVert.size(); i++) {
                int current = oddVert.get(i);
                if (v != current && graph[v][current] < length) {
                    length = graph[v][current];
                    closest = current;
                }
            }
            
            mst.add(new int[]{v, closest, (int) length});
            oddVert.remove((Object) closest);
        }
    }  
    
  public static List<Integer> findEulerianTour(List<int[]> matchedMST, double[][] graph) {
        Map<Integer, List<Integer>> neighbours = new HashMap<>();
        for (int[] edge : matchedMST) {
            if (!neighbours.containsKey(edge[0])) {
                neighbours.put(edge[0], new ArrayList<>());
            }
            if (!neighbours.containsKey(edge[1])) {
                neighbours.put(edge[1], new ArrayList<>());
            }
            neighbours.get(edge[0]).add(edge[1]);
            neighbours.get(edge[1]).add(edge[0]);
        }

        int startVertex = matchedMST.get(0)[0];
        List<Integer> eulerianPath = new ArrayList<>();
        eulerianPath.add(neighbours.get(startVertex).get(0));

        while (!matchedMST.isEmpty()) {
            int i = 0;
            int v = eulerianPath.get(i);
            while (neighbours.get(v).isEmpty()) {
                v = eulerianPath.get(++i);
            }
            int w = neighbours.get(v).get(0);

            removeEdgeFromMatchedMST(matchedMST, v, w);
            neighbours.get(v).remove((Integer) w);
            neighbours.get(w).remove((Integer) v);

            eulerianPath.add(i + 1, w);
        }

        return eulerianPath;
    }
    
    public static List<int[]> removeEdgeFromMatchedMST(List<int[]> matchedMST, int v1, int v2) {
        for (int i = 0; i < matchedMST.size(); i++) {
            int[] edge = matchedMST.get(i);
            if ((edge[0] == v2 && edge[1] == v1) || (edge[0] == v1 && edge[1] == v2)) {
                matchedMST.remove(i);
                break;
            }
        }
        return matchedMST;
    }
    
    public static double getMSTLength(double[][] graph, List<Integer> mst) {
        double length = 0;
//        for (int[] edge : mst) {
//            length += graph[edge[0]][edge[1]];
//        }
        
        for(int i = 0; i < mst.size() - 1; i++){
            length += graph[mst.get(i)][mst.get(i+1)];
        }
        return length;
    }
    
    

  public static Edge[] GetMST(double[][] graph) {
        int V = graph.length;
        EdgeWeightedGraph G = new EdgeWeightedGraph(V);

        for (int i = 0; i < V; i++) {
            for (int j = i + 1; j < V; j++) {
                if (graph[i][j] != 0) {
                    Edge e = new Edge(i, j, graph[i][j]);
                    G.addEdge(e);
                }
            }
        }

        Prims mst = new Prims(G);
        ArrayList<Edge> edges = new ArrayList<>();
        for (Edge e : mst.mst()) {
            edges.add(e);
        }
        Edge[] mstArray = new Edge[edges.size()];
        edges.toArray(mstArray);
        return mstArray;
    }
}
