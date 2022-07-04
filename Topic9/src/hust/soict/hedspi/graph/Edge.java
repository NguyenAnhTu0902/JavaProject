package hust.soict.hedspi.graph;

import java.util.ArrayList;
import java.util.Scanner;

public class Edge {
    private Vertex from;
    private Vertex to;

    private ArrayList<Vertex> myVertex;

    private boolean isWeighed;
    private boolean isDirected;
    private float weight;

    public Edge(Vertex from, Vertex to, ArrayList<Vertex> myVertex, boolean isWeighed, boolean isDirected, float weight) {
        this.from = from;
        this.to = to;
        this.myVertex = myVertex;
        this.isWeighed = isWeighed;
        this.isDirected = isDirected;
        this.weight = weight;
    }

    public Vertex getFrom() {
        return from;
    }

    public Vertex getTo() {
        return to;
    }

    public ArrayList<Vertex> getMyVertex() {
        return myVertex;
    }

    public boolean isWeighed() {
        return isWeighed;
    }

//    public boolean isDirected() {
//        return isDirected;
//    }

    public float getWeight() {
        return weight;
    }

    public void setFrom(Vertex from) {
        this.from = from;
    }

    public void setTo(Vertex to) {
        this.to = to;
    }

    public void setMyVertex(ArrayList<Vertex> myVertex) {
        this.myVertex = myVertex;
    }

    public void setWeighed(boolean weighed) {
        isWeighed = weighed;
    }

    public void setDirected() {
        this.isDirected = true;
        this.from = this.myVertex.get(0);
        this.to = this.myVertex.get(1);
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setUnDirected(){
        this.isDirected = false;
        this.from = null;
        this.to = null;
    }

    public boolean equals(Edge e){
        if (this.isDirected)
            return (this.from.getId() == e.getFrom().getId() && this.to.getId() == e.getTo().getId());

        int i;
        int j = 0, y = 0;
        for (i = 0; i < this.myVertex.size(); i++){
            if (e.getMyVertex().get(0).getId() == this.myVertex.get(i).getId()){
                j = 1;
                break;
            }
        }

        if (j == 0) return false;

        if (e.getMyVertex().get(1).getId() == this.myVertex.get(1-i).getId()){
            y = 1;
        }

        return j == y;
    }
}
