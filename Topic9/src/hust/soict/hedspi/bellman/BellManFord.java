package hust.soict.hedspi.bellman;

import java.lang.reflect.Array;
import java.util.ArrayList;

import hust.soict.hedspi.graph.Edge;
import hust.soict.hedspi.graph.Graph;
import hust.soict.hedspi.graph.Vertex;

public class BellManFord {
    public static final int INFINITE = 1000;
    ArrayList<Vertex> graphVertex = new ArrayList<Vertex>();
    ArrayList<Vertex> checkVertex = new ArrayList<Vertex>();
    ArrayList<Edge> graphEdge = new ArrayList<Edge>();

    private int start;
    private int target;
    private Graph graph;

    public BellManFord(){
        super();
    }

    //todo: tim dinh ke va add vao check Vertex

    public void traverAndAdd(Graph graph,Vertex vertex, ArrayList<Vertex> checkVertex){
        int check =0;
        for (int i=0; i< graph.getListEdge().size(); i++){
            if (graph.getListEdge().get(i).getFrom() == vertex){

                for (int j=0; j< checkVertex.size();i++){       //check getTo co ton tai trong check vertex chua
                    if(graph.getListEdge().get(i).getFrom() == checkVertex.get(j)){
                        check+=1;
                    }
                }
                if (check == 0) {
                    graph.getListEdge().get(i).getTo().setPre(vertex.getId());    //previous la dinh ke

                    float weight = graph.getWeight(vertex, graph.getListEdge().get(i).getTo());
                    graph.getListEdge().get(i).getTo().setDis(weight);

                    checkVertex.add(graph.getListEdge().get(i).getTo());
                }
            } 
        }
    }

    public void Relaxing1 (Graph g, Vertex ver1, Vertex ver2){   //a:ver1----> b:ver2
        // trọng số nhỏ hơn D ban đầu là oo thì cập nhật
        if (ver1.getDis()+g.getWeight(ver1, ver2) < ver2.getDis()){    //d[a]+ab < d[b]
            float newDis = ver1.getDis()+ g.getWeight(ver1, ver2);
            
            ver2.setDis(newDis);      //d[b] ::= d[a]+ab
            ver2.setPre(ver1.getId());
        }
    }

    //todo: tim ra vertex co distance nho nhat
    public Vertex extractMin (ArrayList<Vertex> checkVertex){
        Vertex temp = new Vertex();
        float minDis =  INFINITE;
        for (int i=0; i < checkVertex.size(); i++){
            if (checkVertex.get(i).getDis() < minDis){
//                System.out.println(" Minimum is : "+checkVertex.get(i).getId());
                minDis = checkVertex.get(i).getDis();
                temp = checkVertex.get(i);
            }
        }
        if (minDis == INFINITE){
            return null;
        }else{
            System.out.println("Minium ID is : " + temp.getId());
//            int i = temp.getId();
//            checkVertex.get(i).setVisited(true);
            return temp;
        }
    }

    public void in_duong(Graph g, Vertex v, Vertex start){
        if (v != start){
            in_duong(g, g.getVertex(v.getPre()), start);
            System.out.print("->"+ v.getPre());
        }
    }

}