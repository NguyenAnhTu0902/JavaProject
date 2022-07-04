package hust.soict.hedspi.graph;


import java.util.ArrayList;

public class DFSCycle{
	private int[] v;
	private int[] s;
	private int p1,p2;
	private int n;
	private int[][] input;
	private ArrayList<ArrayList<Integer>> path=new ArrayList<>();
	private ArrayList<Integer> PointPossible=new ArrayList<>();
	private long start;
	private boolean exit=false;
	public DFSCycle(int[][] matrix,int P1,int P2) {
           this.input=matrix;
           this.n=input.length;
           PointPossible=new ArrayList<>();
           path=new ArrayList<>();
		   v=new int[n];
		   s=new int[2*n];		   
		   p1=P1;
		   p2=P2;
       for(int i=0;i<n;i++) {
    	   v[i]=2;
       }
   
     
	}
	public void doDFS(int type) {
		 start =System.currentTimeMillis();
		 if(type==0) {
		      DFSloop(p1,0);
		 }
		 else {
		      DFSloop2(p1,0); 
		 }
	}
	public void setPointPossible(ArrayList<Integer> PointP) {
		this.PointPossible=PointP;
	}
	public ArrayList<ArrayList<Integer>> getPath() {
		return path;
	}
	public void DFSloop2(int a,int idx) {
    	v[a]-=1;
		s[idx]=a;
		 
		if(System.currentTimeMillis()-start>5000) {
			 exit=true;
			 return;
		}
		
		if(a==p2) {
			ArrayList<Integer> temp=new ArrayList<>();
			 for(int i=0;i<=idx;i++) {
				   temp.add(s[i]);
			   }
			 path.add(temp);
			 long max=2147483647;
			 if(Runtime.getRuntime().totalMemory()>max) {
				 exit=true;
				 return;
			 }
			 if(path.size()>=1000000) {
				 exit=true;
				 return;
		
			 }
		   v[a]=2;
		   return ;
		} 
		for(int i=0;i<PointPossible.size();i++) {
			if(!exit) {
		  if(v[PointPossible.get(i)]>=1&&input[PointPossible.get(i)][a]==1) {
			  if(!exit) {
			  DFSloop2(PointPossible.get(i),idx+1);
			  }
		  }
		}
		}
		v[a]+=1;
		
    }
	public void DFSloop(int a,int idx) {
		v[a]-=1;
		s[idx]=a;
		if(System.currentTimeMillis()-start>5000) {
			 exit=true;
			 return;
		}
		if(a==p2) {
			ArrayList<Integer> temp=new ArrayList<>();
			 for(int i=0;i<=idx;i++) {
				   temp.add(s[i]);
			   }
			 path.add(temp);
			 long max=2147483647;
			 if(Runtime.getRuntime().totalMemory()>max) {
				 exit=true;
				 return;
			 }
			 if(path.size()>=1000000) {
				 exit=true;
				 return;
		
			 }
		   v[a]=2;
		   return ;
		} 
		for(int i=0;i<n;i++) {
			if(!exit) {
		       if(v[i]>=1&&input[i][a]==1) {
			      if(!exit) {
			       DFSloop(i,idx+1);
			      }
		       }
		    }
		}
		v[a]+=1;
	}

}
