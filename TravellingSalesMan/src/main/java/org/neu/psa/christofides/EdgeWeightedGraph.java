/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.neu.psa.christofides;

/**
 *
 * @author varun
 */
public class EdgeWeightedGraph
{
final int V;
private final Bag<Edge>[] adj;
public EdgeWeightedGraph(int V)
{
this.V = V;
adj = (Bag<Edge>[]) new Bag[V];
for (int v = 0; v < V; v++)
adj[v] = new Bag<Edge>();
}
public void addEdge(Edge e)
{
int v = e.either(), w = e.other(v);
adj[v].add(e);
adj[w].add(e);
}
public Iterable<Edge> adj(int v)
{ return adj[v]; }
}
