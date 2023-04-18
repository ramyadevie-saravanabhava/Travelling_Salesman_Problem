/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.neu.psa.christofides;

/**
 *
 * @author varun
 */
public class Edge implements Comparable<Edge>
{
private final int v, w;
final double weight;
public Edge(int v, int w, double weight)
{
this.v = v;
this.w = w;
this.weight = weight;
}
public int either()
{ return v; }
public int other(int vertex)
{
if (vertex == v) return w;
else return v;
}
public int compareTo(Edge that)
{
if (this.weight < that.weight) return -1;
else if (this.weight > that.weight) return +1;
else return 0;
}
}
