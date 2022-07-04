package hust.soict.hedspi.graph;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
@SuppressWarnings("unused")
public class MyGraph extends JPanel{
	private static final long serialVersionUID = 1L;
	private Color colorBackGround=Color.white;
	private Color EdgeColor=Color.black;
	 private Color PointColor=Color.black;
	 private String BgColor="#FFFFFF";
	 private String EColor="#000000";
	 private String PColor="#FFFFFF";
	 private String FontColor="#000000";
	 private String Layout="dot"; 
	 private String PointShape="circle";
	 private String EdgeShape="solid";
	 private String EdgeShapePast="solid";
	 private String Rankdir="TB";
	 private String ArrowShape="normal";
	 private String Splines="spline";
	 private final String[] Hexa= {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
	 private Image image; 
	 private JPanel mypanel;
	 private JLabel imageLabel;	 
	 private int[][] output;
	 private int[][] output_matrix;
	 private int GraphType=0;
	 private int[] pastEdge;
	 private int EdgeCount=0;
	 private ArrayList<String> Point=new ArrayList<String>();
	 private ArrayList<ArrayList<Integer>> inputSimulation=new ArrayList<>();
	 private int n=0;
	 private double maxHeight=0,maxWidth=0;
	 ReadFile Rf;
	 ReadMatrixInput Rf2;
	 String Dot_source="Source/test.dot";
	 String Image_source="Source/dotTest.png";
	 String Graphviz_source="Source/Graphviz/bin/dot.exe";
     public MyGraph() {
    	 setLayout(new FlowLayout());
    	 mypanel=new JPanel();    	
     }
     public void MakeGraph() throws IOException {
	    makeDot();
	    revalidate();
	    repaint();  
     }
     public void updateGraph() {
    	 try {
   			resetGraph();
  			MakeGraph();
  		} catch (IOException e) {
  			e.printStackTrace();
  		}
     }
     public void resetGraph() {
    	mypanel.removeAll();
 	    removeAll();
 	    revalidate();
 	    repaint();  
     }
     public void resetData() {
    	 Point=new ArrayList<String>(); 
    	 EdgeCount=0;
     }
     public void resetSimulation() {
    	 inputSimulation=new ArrayList<>();
    	 EdgeShape=EdgeShapePast;
    	 
     }
     public int[][] convertListToMatrix(int Input[][],int n) {
		  int[][] output=new int[n][n];
		   for(int i=0;i<Input.length;i++) {
			   int j=1;
			   while(j<Input[i].length&&Input[i][j]!=0) {
				   output[Input[i][j]-1][Input[i][0]-1]=1;
				   j++;
			   }
		   }
		return output;   
 }
     public void updateSimulationData(ArrayList<ArrayList<Integer>> input){
    	 this.inputSimulation=input;
    	 EdgeShape="dotted";   	 
     }
      public String convertDecToHex(int n) {
      	int temp=n;
      	String strtemp1="";
      	String strtemp2="";
      	if(temp==0) {
      		strtemp1+="00";
      	}
      	while(temp>0) {
      		strtemp1+=Hexa[temp%16];
      		temp=temp/16;
      	}
      	for(int i=strtemp1.length()-1;i>=0;i--) {
      		strtemp2+=strtemp1.charAt(i);
      	}
      	return strtemp2;
      	
      }
     public void Resize(double width,double height) {
    	 this.maxHeight=height;
    	 this.maxWidth=width;
     }
     public void makeDot() throws IOException {
 		String dot="";
 	    boolean RedLine=false;
 	    boolean BlueLine=false;
 	    dot+="digraph{layout=\""+Layout+"\";rankdir="+Rankdir+";outputorder=edgesfirst;splines=\""+Splines+"\";";
 	    dot+= "graph[bgcolor=\""+BgColor+"\",size=\""+maxWidth+","+maxHeight+"\",overlap=scale];";
		dot+="node[shape=\""+PointShape+"\",style=filled,fillcolor=\""+PColor+"\",fontcolor=\""
 	    +FontColor+"\"];";
		dot+="edge[arrowhead=\""+ArrowShape+"\"];";
 		for(int i=0;i<Point.size();i++){
 			dot+="\""+Point.get(i)+"\";";
 		}
 		for(int i=0;i<n;i++) {
 			int j=1;
 			while(j<=n) {
 				
 				if(output[i][j]!=0) {
 				RedLine=false;
 				BlueLine=false;
 				if(pastEdge!=null) {
 					if((output[i][0]-1)==pastEdge[0]&&(output[i][j]-1)==pastEdge[1]) {
 					dot+=("\""+Point.get(pastEdge[0])+"\""+"->"+"\""
 	 						+Point.get(pastEdge[1])+"\""+"[color=blue,style=bold]");
 					BlueLine=true;
 					pastEdge=null;
 					}
 				}
 				if(!BlueLine) {
 				for(int idx=0;idx<inputSimulation.size();idx++) {
 					if((output[i][0]-1)==inputSimulation.get(idx).get(0)
 					  &&(output[i][j]-1)==inputSimulation.get(idx).get(1)) {
 						int idx1=output[i][0]-1;
 						int idx2=output[i][j]-1;
 						dot+=("\""+Point.get(idx1)+"\""+"->"+"\""
 						+Point.get(idx2)+"\""+"[color=red,style=bold]"
 						+";");	
 						RedLine=true;
 						break;
 					}
 				}
 				}
 				if(!RedLine&&!BlueLine) {
 				
 				dot+=("\""+Point.get(output[i][0]-1)+"\""+"->"+"\""+Point.get(output[i][j]-1)+"\""+"[color=\""+EColor+"\""
 						+ ",style=\""+EdgeShape+"\"]"+";");
 				}
 				
 				j++;
 				}
 				else {
 					j++;
 				}
 			}			
 		}
 		 dot+=("}");
 		 try {
 			 BufferedWriter br=new BufferedWriter(new FileWriter(Dot_source));
 			 br.write(dot);
 	 		 br.flush();
 	 		 br.close();
 		 }
 		 catch(FileNotFoundException ex){
 			JOptionPane.showMessageDialog(null,
					"Please install file \"Source\" to your Project file ",
					"Error", JOptionPane.WARNING_MESSAGE);
			System.exit(0);
 		 }
 		 try {
 	      Process p=Runtime.getRuntime().exec(Graphviz_source+" -Tpng "+Dot_source
 	 	    		 +" -o "+Image_source);
 	 	  p.waitFor();
 	     }
 		 catch(IOException ioe) {}
 		 catch(InterruptedException ie) {}
 	     showGraph(); 
 	}
    public void showGraph() {
    	 try {
  			image= ImageIO.read(new File(Image_source));
          } catch (IOException e) {
        		JOptionPane.showMessageDialog(null,
						"Please install file \"Source\" to your Project file ",
						"Error", JOptionPane.WARNING_MESSAGE);
				System.exit(0);
          }
     	add(Jpanel());
    }
    public JPanel Jpanel() {
 		imageLabel = new JLabel(new ImageIcon(image)) ;			
 	    mypanel.setLayout(new BorderLayout());
 	    mypanel.add(imageLabel, BorderLayout.CENTER); 	    
 		return mypanel;
 	}
    public void paintComponent(Graphics g) {
 		super.paintComponent(g);
 		setBackground(colorBackGround);	
    }
    public void setGraphType(int type) {
   	 this.GraphType=type;
   	 if(type!=0) {
   	   this.Splines="false";
   	   this.Layout="neato";
   	 }
    }
    public void setSimulation(){
   	 EdgeShapePast=EdgeShape;
   	 EdgeShape="dotted";
   	 
    }
    public void setBackgroundColor(Color color) {
     	colorBackGround=color;
     	BgColor="#";
  		int r=color.getRed();
  		int g=color.getGreen();
  		int b=color.getBlue();
  		BgColor+=convertDecToHex(r);
  		BgColor+=convertDecToHex(g);
  		BgColor+=convertDecToHex(b); 
  		updateGraph();
  		
  	}
  	public void setEdgeColor(Color color) {
  		EColor="#";
  		int r=color.getRed();
  		int g=color.getGreen();
  		int b=color.getBlue();
  		EColor+=convertDecToHex(r);
  		EColor+=convertDecToHex(g);
  		EColor+=convertDecToHex(b); 
  		updateGraph();
  		
  	}
	public void setFontColor(Color color) {
  		FontColor="#";
  		int r=color.getRed();
  		int g=color.getGreen();
  		int b=color.getBlue();
  		FontColor+=convertDecToHex(r);
  		FontColor+=convertDecToHex(g);
  		FontColor+=convertDecToHex(b); 
  		updateGraph();
  		
  	}
  	public void setPointColor(Color color) {
  		PColor="#";
  		int r=color.getRed();
  		int g=color.getGreen();
  		int b=color.getBlue();
  		PColor+=convertDecToHex(r);
  		PColor+=convertDecToHex(g);
  		PColor+=convertDecToHex(b); 
  		updateGraph();
  	}
  	public void setPointShape(String shape) {
  		this.PointShape=shape;
  		if(shape.compareTo("point")==0) {
  			maxHeight=Point.size()*3;
  	 	    maxWidth=Point.size()*9/2;
  		}
  		else {
  			maxHeight=Point.size();
  	 	    maxWidth=Point.size()*3/2;
  		}
  		updateGraph();
  	}
	public void setEdgeShape(String shape) {
  		this.EdgeShape=shape;
  		updateGraph();
  	}
	public void setSpline(String shape) {
		if(GraphType==0) {
  		this.Splines=shape;
  		updateGraph();
		}
  	}
	public void setArowhead(String shape) {
  		this.ArrowShape=shape;
  		updateGraph();
  	}
	public void setLayout(String layout) {
		if(GraphType==0) {
  		this.Layout=layout;
  		updateGraph();
		}
  	}
	public void setRankdir(String dir) {
  		this.Rankdir=dir;
  		updateGraph();
  	}
	public void setpathEdge(int[] edge) {
		this.pastEdge=edge;
	}
  	public void setDefault() {
  		if(GraphType==0) {
  		maxHeight=Point.size();
 	    maxWidth=Point.size()*3/2;
  		 colorBackGround=Color.white;
  		 EdgeColor=Color.black;
  		 PointColor=Color.black;
  		 BgColor="#FFFFFF";
  		 EColor="#000000";
  		 PColor="#FFFFFF";
  		 Layout="dot"; 
  		 PointShape="circle";
  		 EdgeShape="solid";
  		 ArrowShape="normal";
  		 Splines="spline";
  		 Rankdir="LR";
  		 updateGraph();
  		}
  	}
  	public void setEdgeCount(int count) {
  		this.EdgeCount=count;
  	}
  	public void getDataFromListFile(String url) {
    	Rf=new ReadFile(url);
  	    output=Rf.read();
  	    Point=Rf.getPoint();
 	    n=Point.size(); 
 	    if(n<=100) {
 	    maxHeight=Point.size();
 	    maxWidth=Point.size()*3/2;
 	    }
 	    else if(n>100&&n<200){
 	    	maxHeight=Point.size()/4;
 	 	    maxWidth=Point.size()*3/8;	
 	    }
 	    else {
 	    	maxHeight=Point.size()/10;
 	 	    maxWidth=Point.size()*3/20;	
 	    }
 	    output_matrix=convertListToMatrix(output, n);
 	    EdgeCount=Rf.getEcount();
 	    //System.out.println(EdgeCount);
     }
     public int getDataFromMatrixFile(String url) {
    	 Rf2=new ReadMatrixInput(url);
    	 output_matrix=Rf2.read();
    	 if(output_matrix==null) {
    		 JOptionPane.showMessageDialog(null,
						"Some error occurred, please check your input data!",
						"Error", JOptionPane.WARNING_MESSAGE);
    		 return 1;
   
    	 }
    	 else {
    		 output=Rf2.getListOutput();
    		 Point=Rf2.getPoint();
    		 n=Point.size();
    		 if(n<=100) {
    		 	    maxHeight=Point.size();
    		 	    maxWidth=Point.size()*3/2;
    		 	    }
    		 	    else if(n>100&&n<200){
    		 	    	maxHeight=Point.size()/4;
    		 	 	    maxWidth=Point.size()*3/8;	
    		 	    }
    		 	    else {
    		 	    	maxHeight=Point.size()/10;
    		 	 	    maxWidth=Point.size()*3/20;	
    		 	    }
    	 	 return 0;
    	 }
     }
     public void getData(ArrayList<String> point,int[][] Output) {
    	 this.Point=point;
    	 this.output=Output;
    	 n=Point.size();
    	 if(n<=100) {
    	 	    maxHeight=Point.size();
    	 	    maxWidth=Point.size()*3/2;
    	 	    }
    	 	    else if(n>100&&n<200){
    	 	    	maxHeight=Point.size()/4;
    	 	 	    maxWidth=Point.size()*3/8;	
    	 	    }
    	 	    else {
    	 	    	maxHeight=Point.size()/10;
    	 	 	    maxWidth=Point.size()*3/20;	
    	 	    }
  	     output_matrix=convertListToMatrix(Output, n);
     }
    public int getEcount() {
    	return this.EdgeCount;
    }
  	public int getGraphType() {
		return this.GraphType;
	}
	public int getImageWidth() {
		return this.imageLabel.getWidth();
	}
	public int getImageHeight() {
		return this.imageLabel.getHeight();
	}
	public ArrayList<String> getPoint() {
		return this.Point;
	}
	public int[][] getOutput_matrix(){
		return this.output_matrix;
	}
	public Image getImage() {
		return this.image;
	}
	
}
