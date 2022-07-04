package hust.soict.hedspi.main;

import java.util.ArrayList;
import java.util.Scanner;

import hust.soict.hedspi.bellman.BellManFord;
import hust.soict.hedspi.graph.Draw;
import hust.soict.hedspi.graph.Graph;
import hust.soict.hedspi.graph.Vertex;

public class Main {
    public static final float INFINITE = 1000;

    public static void main(String[] args) {
        // write your code here
        ArrayList<Vertex> checkVertex = new ArrayList<Vertex>();

        Draw dr = new Draw();
        Graph testGraph = dr.drawGraph();
        testGraph.showGraph();

        //ae test cac truong hop ben duoi
        BellManFord bellmanford = new BellManFord();

        System.out.print("Nhap id dinh Start: ");
        Scanner sc1 = new Scanner(System.in);
        int startID = sc1.nextInt();

        Vertex Start = new Vertex();
        Start = testGraph.getVertex(startID);

        for (int i=0; i<testGraph.getListVertex().size(); i++){
            if (testGraph.getListVertex().get(i) == Start){
                testGraph.getListVertex().get(i).setPre(0);
                testGraph.getListVertex().get(i).setDis(0.0f);
            }
        }

        checkVertex.addAll(testGraph.getListVertex());

        for (int i = 0; i < testGraph.getListVertex().size(); i++) {  //todo: khoi tao vo cung
            testGraph.getListVertex().get(i).setDis(INFINITE);
        }
        Start.setDis(0.0f);
        Start.setPre(0);
        Start.setVisited(true);
        if (testGraph.numOutdegree(Start) == 0){
            System.out.println("Khong co dinh ke cua "+Start.getId());
        }else {
            while (checkVertex.size() != 0) {
                boolean check = true;
                System.out.println(" Hello ");
                System.out.println("Checkvertex size is : " + checkVertex.size());
                for (int i = 0; i < testGraph.getListVertex().size(); i++) {
                    System.out.println("---------------Lan lap thu " + i + "---------------------");

                    Vertex temp = new Vertex();

                    System.out.println("Danh sach checkVertex truoc khi relax");
                    temp = bellmanford.extractMin(checkVertex);
                    for (int k = 0; k < checkVertex.size(); k++) {
                        checkVertex.get(k).showVerInfor();
                    }

                    //duyet tat cac ke cua dinh do
                    for (int j = 0; j < checkVertex.size(); j++) {
                        //neu la dinh ke va chua duoc tham
                        if (testGraph.hasEdgeFrom(temp, checkVertex.get(j)) && checkVertex.get(j) != temp) {     //
                            bellmanford.Relaxing1(testGraph, temp, checkVertex.get(j));
                        }
                    }

                    System.out.println("Danh sach checkVertex sau khi relax");
                    temp = bellmanford.extractMin(checkVertex);
                    for (int k = 0; k < checkVertex.size(); k++) {
                        checkVertex.get(k).showVerInfor();
                    }

                    //remove extractMin
                    if (temp != null) {
                        for (int j = 0; j < checkVertex.size(); j++) {
                            if (checkVertex.get(j).getId() == temp.getId() && checkVertex.get(j) != null) {
                                System.out.println("---");
                                System.out.println("ID remove : " + checkVertex.get(j).getId());
                                checkVertex.remove(j);
                            }
                        }
                    }
                    System.out.println("------------------------------------------------\n");
                }

                for (int i = 0; i < checkVertex.size(); i++) {
                    if (checkVertex.get(i).getDis() != INFINITE) {       //van con ton tai vertex can xet
                        check = false;
                        break;
                    }
                }
                if (check) {
                    break;
                }
            }

            System.out.println("Danh sach cac dinh sau BellManFord");
            for (int k = 0; k < testGraph.getListVertex().size(); k++) {
                testGraph.getListVertex().get(k).showVerInfor();
            }
            System.out.println("\n---------BellManFord---------");
            for (int i = 0; i < testGraph.getListVertex().size(); i++) {
                System.out.print("(" + testGraph.getListVertex().get(i).getId() + ", " + testGraph.getListVertex().get(i).getDis() + "): ");
                if (testGraph.getListVertex().get(i).getDis() == INFINITE) {
                    System.out.println("Khong co duong di tu " + Start.getId() + " den " + testGraph.getListVertex().get(i).getId());
                } else {
                    bellmanford.in_duong(testGraph, testGraph.getListVertex().get(i), Start);
                    System.out.println("->" + testGraph.getListVertex().get(i).getId());
                }
                System.out.println("----------");
            }
        }
    }
}



