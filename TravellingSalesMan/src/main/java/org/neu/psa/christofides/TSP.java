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
import org.neu.psa.model.Location;


public class TSP {

    public static Location [] Locations;
    public static List<Edge> edge;
    static class Point {
        double x, y;
        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
        public double distanceTo(Point other) {
            double dx = x - other.x;
            double dy = y - other.y;
            return Math.sqrt(dx * dx + dy * dy);
        }
    }

    public static void main(String[] args) {
        List<Point> points = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\varun\\OneDrive\\Desktop\\PSA PROJECT\\INFO-6205-Spring-2023\\TravellingSalesMan\\crimeSample.csv"))) {
            String line;
             List<String> lines = new ArrayList<>();
//            while ((line = br.readLine()) != null) {
//                String[] values = line.split(",");
//                double latitude = Double.parseDouble(values[1]);
//                double longitude = Double.parseDouble(values[2]);
//                points.add(new Point(longitude, latitude));
//            }

            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            
            Locations = new Location[lines.size()];
            for(int i=0; i<Locations.length; i++){
                String[]parts = lines.get(i).split(",");
                Locations[i] = new Location (i+1, parts[0].substring(parts[0].length() - 5),Double.parseDouble(parts[1]), Double.parseDouble(parts[2]) );
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        int n = Locations.length;
        double[][] graph = new double[n][n];
        for (int i = 0; i < n-1; i++) {
            for (int j = i + 1; j < n; j++) {
                double distance = Locations[i].distanceTo(Locations[j]);
                graph[i][j] = distance;
                graph[j][i] = distance;
            }
        }

        List<int[]> mst = prim(graph, 0);
    
//        for (int[] edge : mst) {
//            System.out.println(Arrays.toString(edge));
//        }
//        List<Integer> oddVertices = new ArrayList<>();
        List<Integer> oddVertices = findOddVertexes(mst);
        
        minimumWeightMatching(mst, graph, oddVertices);
        
//        List<Integer> eularian = findEulerianTour(mst, graph);
//        System.out.println("Eularian path: " + eularian);
        
        List<Integer> eulerian_tour = findEulerianTour(mst, graph);
System.out.println("Eulerian tour: " + eulerian_tour);

int current = eulerian_tour.get(0);
List<Integer> path = new ArrayList<>();
path.add(current);
boolean[] visited = new boolean[eulerian_tour.size()];
visited[eulerian_tour.get(0)] = true;
double length = 0;

for (int v : eulerian_tour) {
    if (!visited[v]) {
        path.add(v);
        visited[v] = true;
        length += graph[current][v];
        current = v;
    }
}

length += graph[current][eulerian_tour.get(0)];
path.add(eulerian_tour.get(0));

System.out.println("Result path: " + path);
System.out.println("Result length of the path: " + length);

    }

       public static List<int[]> prim(double[][] graph, int startNode) {
        int v = graph.length;
        int[] parent = new int[v];
        
        int[] key = new int[v];
        
        Boolean mstSet[] = new Boolean[v];
        
        for(int i = 0; i < v; i++){
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }
        
        key[startNode] = 0;
        parent[startNode] = -1;
        
        for(int count = 0; count < v -1; count++){
            int u = minKey(key, mstSet);
            
            mstSet[u] = true;
            
            for(int n = 0; n < v; n++){
                
                if(graph[u][n]!=0 && mstSet[n]== false && graph[u][n] < key[n] ){
                    parent[n] = u;
                    key[n] = (int) graph[u][n];
                }
            }
                
                
        }
        
        List<int[]> mst = new ArrayList<>();
        
        for(int f = 1; f< v; f++){
            
            int[] m = {parent[f], f};
            
            mst.add(m);
        }
        return mst;
    }
       
     static int minKey(int key[], Boolean mstSet[])
    {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;
 
        for (int v = 0; v < Locations.length; v++)
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

    static List<Integer> hamiltonianCircuit(List<Integer> circuit) {
        Set<Integer> visited = new HashSet<>();
        List<Integer> tour = new ArrayList<>();
        for (int i = 0; i < circuit.size(); i++) {
            int node = circuit.get(i);
            if (!visited.contains(node)) {
                visited.add(node);
                tour.add(node);
            }
        }
        return tour;
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
        // find neighbours
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

        // find the Hamiltonian circuit
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
  
}
