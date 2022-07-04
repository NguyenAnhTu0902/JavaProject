package hust.soict.hedspi.graph;

public class Vertex {
		private int id;

	    private int pre;    //previous Vertex's ID
	    private float dis;    //distance from Start Node to this vertex
	    
	    
	    private boolean visited;    // travered or not, true or false
	   

	    public void setVisited(boolean visited){
	        this.visited = visited;
	    }

	    public boolean getVisted(){
	        return this.visited;
	    }

	    public int getId() {
	        return id;
	    }

	    public void setPre(int pre){
	        this.pre = pre;
	    }

	    public int getPre(){
	        return pre;
	    }

	    public void setDis( float dis){
	        this.dis = dis;
	    }

	    public float getDis(){
	        return dis;
	    }

//	    public void setId(int id) {
//	        this.id = id;
//	    }
	    public Vertex (){
	        super();
	    }
	    public Vertex(int id) {
	        this.id = id;
	    }
	    //todo: compare vertex
	    public boolean equals(Vertex v){
	        return this.id == v.getId();
	    }

	    public void showVerInfor(){
//	        System.out.println("---");
	        System.out.print("ID : "+this.getId());
	        System.out.println("("+this.getDis()+", "+this.getPre()+")");
	    }
}
