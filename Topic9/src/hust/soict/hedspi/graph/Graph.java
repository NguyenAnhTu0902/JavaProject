package hust.soict.hedspi.graph;

import java.util.ArrayList;

public class Graph {
	private ArrayList<Edge> listEdge;
    private ArrayList<Vertex> listVertex;

    private boolean isDirected = true;

    public Graph(ArrayList<Edge> listEdge, ArrayList<Vertex> listVertex) {
        this.listEdge = listEdge;
        this.listVertex = listVertex;
    }

    public ArrayList<Edge> getListEdge() {
        return listEdge;
    }

    public ArrayList<Vertex> getListVertex() {
        return listVertex;
    }

    public void setListEdge(ArrayList<Edge> listEdge) {
        this.listEdge = listEdge;
    }

    public void setListVertex(ArrayList<Vertex> listVertex) {
        this.listVertex = listVertex;
    }

    public boolean isDirected() {
        return isDirected;
    }

    public void setDirected(boolean directed) {
        isDirected = directed;
        if (!isDirected)
            for (int i = 0; i < listEdge.size(); i++){
                listEdge.get(i).setUnDirected();
            }
        else
            for (int i = 0; i < listEdge.size(); i++){
                listEdge.get(i).setDirected();
            }
    }

    public Vertex hasVertex(int id){
        for (int i = 0; i < this.listVertex.size(); i++){
            if (id == this.listVertex.get(i).getId()){
                return this.listVertex.get(i);
            }
        }
        return null;
    }


    public int numOutdegree(Vertex vertex){
        int outDegree=0;
        for (int i=0; i<this.getListEdge().size();i++){
            if (this.getListEdge().get(i).getFrom() == vertex){
                outDegree++;
            }
        }
        return outDegree;
    }

    public boolean hasEdgeFrom(Vertex ver1, Vertex ver2){
//        ArrayList<Vertex> newArray = new ArrayList<Vertex>();
        for (int i =0; i<this.getListEdge().size();i++){
            if (this.getListEdge().get(i).getFrom() == ver1 && this.getListEdge().get(i).getTo() == ver2){
                return true;
//                this.getListEdge().get(i).getTo().setPre(vertex.getId());
            }
        }
        return false;
    }

    public Vertex getVertex (int verID){    //get Vetex from ID
        for (int i=0; i< this.getListVertex().size(); i++){
            if (this.getListVertex().get(i).getId() == verID){
                return this.getListVertex().get(i);
            }
        }
        return null;
    }

    public Boolean hasEdge(Edge e){
        if (this.isDirected){
            for (int i = 0; i < this.getListEdge().size(); i++){
                if (this.getListEdge().get(i).equals(e)){
                    return true;
                }
            }
        } else{
            e.setUnDirected();
            for (int i = 0; i < this.getListEdge().size(); i++){
                if (this.getListEdge().get(i).equals(e)){
                    return true;
                }
            }
        }
        return false;
    }

    //todo: viet ham get weight (int ver1, int ver2
    public float getWeight (Vertex verID1, Vertex verID2){
        for (int i=0; i<this.getListEdge().size();i++){
            if (this.getListEdge().get(i).getFrom() == verID1
            && this.getListEdge().get(i).getTo() == verID2){
                return this.getListEdge().get(i).getWeight();
            }
        }
        return 0.0f ;
    }


    public void showGraph(){
        if (this.isDirected) {
            for (int i = 0; i < listEdge.size(); i++) {
                System.out.println(listEdge.get(i).getFrom().getId() + "->"
                        + listEdge.get(i).getTo().getId() + " = " + listEdge.get(i).getWeight());
            }
        }else {
            for (int i = 0; i < listEdge.size(); i++) {
                System.out.println(listEdge.get(i).getMyVertex().get(0).getId() + "-"
                        + listEdge.get(i).getMyVertex().get(1).getId() + " = " + listEdge.get(i).getWeight());
            }
        }
    }

    public void showVertex(){
        for (int i=0; i<listVertex.size();i++){
            System.out.println("Dinh :"+listVertex.get(i).getId());
            System.out.println("Pre : "+listVertex.get(i).getPre());
            System.out.println("Pre : "+listVertex.get(i).getDis());
        }
    }
}
