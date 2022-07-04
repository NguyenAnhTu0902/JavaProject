package hust.soict.hedspi.graph;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class ReadMatrixInput {
	 private FileInputStream FIS;
	 private Scanner sc;
	 private int output[][];
	 private int outputList[][];
	 private int n=0;
	 private int EdgeCount=0;
	 private ArrayList<String> Point;


	  public ReadMatrixInput(String url) {
		   try {
				 FIS=new FileInputStream(url);
				 sc=new Scanner(FIS);
				 }
		   catch(FileNotFoundException ex){
			   JOptionPane.showMessageDialog(null,
						"File Not Found!",
						"Error", JOptionPane.WARNING_MESSAGE);
		   }
		   Point=new ArrayList<String>();
	      } 
	public int[][] read() {
		try {
			n=sc.nextInt();
			output=new int[n][n];
			outputList=new int[n][n+1];
			for(int i=1;i<=n;i++) {
				Point.add(String.valueOf(i));
			}
		    for(int i=0;i<n;i++) {
				for(int j=0;j<n;j++) {
					output[i][j]=sc.nextInt();
					if(output[i][j]!=0) {
						EdgeCount++;
					}
				}	
			}
			convertToList();
			
		}
		catch(NullPointerException e2) {
			return null;
		}
		catch(NoSuchElementException e3) {
			return null;
			}
		finally{
			   try {
			     sc.close();
			     FIS.close();		   
			   }
			   catch(IOException ex) {
				   return null;
			   }
		   }
		return output;
	}  
    public void convertToList() {
    	for(int i=0;i<n;i++) {
    		outputList[i][0]=i+1;
    		for(int j=0;j<n;j++) {
    			if(output[j][i]!=0) {
    			   outputList[i][j+1]=j+1;	
    			}  			
    		}
    	}
    }
    public int getEdgeCount() {
    	return this.EdgeCount;
    }
    public ArrayList<String> getPoint() {
    	return this.Point;
    }
    public int[][] getListOutput(){
    	return this.outputList;
    }

}
