/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.neu.psa.christofides;

import java.util.Queue;

/**
 *
 * @author varun
 */
import java.util.*;

public class Prims {
    private boolean[] marked;  // MST vertices
    private Queue<Edge> mst;   // MST edges
    private PriorityQueue<Edge> pq;  // PQ of edges

    public Prims(EdgeWeightedGraph G) {
        pq = new PriorityQueue<Edge>();
        mst = new LinkedList<Edge>();
        marked = new boolean[G.V];
        visit(G, 0);
        while (!pq.isEmpty() && mst.size() < G.V - 1) {
            Edge e = pq.poll();
            int v = e.either(), w = e.other(v);
            if (marked[v] && marked[w]) continue;
            mst.offer(e);
            if (!marked[v]) visit(G, v);
            if (!marked[w]) visit(G, w);
        }
    }

    private void visit(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge e : G.adj(v)) {
            if (!marked[e.other(v)]) {
                pq.offer(e);
            }
        }
    }

    public Iterable<Edge> mst() {
        return mst;
    }
}
