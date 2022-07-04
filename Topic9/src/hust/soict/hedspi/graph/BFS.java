package hust.soict.hedspi.graph;
import java.util.ArrayList;


public class BFS {
    private  int[][] inputmatrix;
    private int n=0;
    private int p1,p2;
    public int[] endPossible,beginPossible,possible;
    private ArrayList<Integer> temp=new ArrayList<>();
    private ArrayList<ArrayList<Integer>> Stack=new ArrayList<>();
    private ArrayList<ArrayList<Integer>> Data=new ArrayList<>();
    private ArrayList<Integer> Pointer=new ArrayList<>();
    private ArrayList<Integer> point_temp=new ArrayList<>();
	public BFS(int[][] matrix,int P1,int P2) {
		this.inputmatrix=matrix;
		n=inputmatrix.length;
		this.p1=P1;
		this.p2=P2;
        endPossible=new int[n];
        beginPossible=new int[n];
        possible=new int[n];
        for(int i=0;i<n;i++) {		
        	point_temp.add(i);
        }
        temp.add(p2);
        temp.add(0);
        Stack.add(temp);
        while(Stack.size()!=0) {
        	int v=Stack.get(0).get(0);
        	for(int i=0;i<point_temp.size();i++) {
        		if(inputmatrix[v][point_temp.get(i)]==1) {
        			temp=new ArrayList<>();
        			temp.add(point_temp.get(i));
        			temp.add(Stack.get(0).get(1)+1);	
        			Data.add(temp);
        			Stack.add(temp);
        			endPossible[point_temp.get(i)]=1;
        			point_temp.remove(i);
        			i--;
        		}
        	}
        	Stack.remove(0);
        }
        beginPossible[p1]=1;
        for(int i=0;i<Data.size();i++) {
             point_temp.add(Data.get(i).get(0));
        }
        temp=new ArrayList<>();
        temp.add(p1);
        temp.add(0);
        Stack.add(temp);
        while(Stack.size()!=0) {
        	int v=Stack.get(0).get(0);
        	for(int i=0;i<point_temp.size();i++) {
        		if(inputmatrix[point_temp.get(i)][v]==1) {
        			temp=new ArrayList<>();
        			temp.add(point_temp.get(i));
        			temp.add(Stack.get(0).get(1)+1);	
        			Stack.add(temp);
        			beginPossible[point_temp.get(i)]=1;
        			point_temp.remove(i);
        			i--;
        		}
        	}
        	Stack.remove(0);
        }
        endPossible[p2]=1;
        for(int i=0;i<n;i++) {
        	if(endPossible[i]==1&&beginPossible[i]==1) {
        		possible[i]=1;
        	}
        }
       
        
        for(int i=0;i<Data.size();i++) {
        	if(possible[Data.get(i).get(0)]==0) {
        		Data.remove(i);
        		i--;
        	}
        }   
	}
	public ArrayList<Integer> getPossiblePoint(){
		for(int i=0;i<Data.size();i++) {
			point_temp.add(Data.get(i).get(0));
		}
        point_temp.add(p2);
		return point_temp;
	}
	public int[] getMinNearBy(int idx) {
		int near[]=new int[n];
	    int rank=0;
	    for(int i=0;i<Data.size();i++) {
	    	if(Data.get(i).get(0)==idx) {
	    		rank=Data.get(i).get(1);
	    	}    	
	    }
	    near[p2]=1;
	    if(rank>1) {
	    for(int i=Pointer.get(rank-2);i<Pointer.get(rank-1);i++) {
	    	if(inputmatrix[Data.get(i).get(0)][idx]==1) {
	    		near[Data.get(i).get(0)]=1;
			}
	    }
	    }
		return near;
	}
	public ArrayList<Integer> FindMinWay(int p) {
		point_temp=new ArrayList<>();
	    Pointer =new ArrayList<>();
		ArrayList<Integer> min=new ArrayList<>(); 
		Pointer.add(0);
		int idx=1;
		for(int i=0;i<Data.size();i++) {
			point_temp.add(Data.get(i).get(0));
			if(Data.get(i).get(1)>idx) {
				Pointer.add(i);
				idx++;				
			}
		}
		int rank=0;
		if(point_temp.indexOf(p)!=-1) {
		   rank=Data.get(point_temp.indexOf(p)).get(1);
		}
		else {
			return min;
		}
		min.add(p);
		while(rank>1) {
			for(int i=Pointer.get(rank-2);i<Pointer.get(rank-1);i++) {
				if(inputmatrix[point_temp.get(i)][p]==1) {
					min.add(point_temp.get(i));
					p=point_temp.get(i);
					break;
				}
			}
		    rank--;
		}
		min.add(p2);
		return min;
	}

}
