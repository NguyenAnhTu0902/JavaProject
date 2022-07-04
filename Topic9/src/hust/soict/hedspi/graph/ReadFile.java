package hust.soict.hedspi.graph;

import java.util.Scanner;

import javax.swing.JOptionPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.ArrayList;

import java.io.IOException;
public class ReadFile {
	 private FileInputStream FIS;
	 private Scanner sc;
	 private int output[][];
	 private int n=0;
	 private int Ecount=0;
	 private ArrayList<ArrayList<String>> InputString=new ArrayList<>();
	 private ArrayList<String> Point;
   public ReadFile(String url) {
	   try {
			 FIS=new FileInputStream(url);
			 sc=new Scanner(FIS);
			 }
	   catch(FileNotFoundException ex){
		   JOptionPane.showMessageDialog(null,
					"File Not Found!",
					"Error", JOptionPane.WARNING_MESSAGE);
	   }
	   InputString=new ArrayList<ArrayList<String>>();
	   Point=new ArrayList<String>();
      }
   public int[][] read() {
	  try {
		  while(sc.hasNextLine()) {
			  getString(sc.nextLine());
		  }
	  }
	  finally{
		   try {
		     sc.close();
		     FIS.close();		   
		   }
		   catch(IOException ex) {}
	   }
	  setoutput();
	  return output;
   }
   public ArrayList<String> getPoint(){
	   return Point;
   } 
   public int getEcount() {
	   return this.Ecount;
   }
   public void getString(String input) {
	   ArrayList<String> temp_s=new ArrayList<String>();
       Scanner sc=new Scanner(input);
       while(sc.hasNext()) {
    	   String temp=sc.next();
    	   if(!Point.contains(temp)) {
    		   Point.add(temp);
    	   }
    	   temp_s.add(temp);
    	   Ecount+=temp_s.size();
       }
	   InputString.add(temp_s);
	   sc.close();
   } 
public void setoutput() {
	  n=Point.size();
	  if(InputString.size()>=n) {
	     output=new int[InputString.size()][n+1];
	  }
	  else {
		 output=new int[n][n+1];  
	  }
      for(int i=0;i<InputString.size();i++) {
		  output[i][0]=Point.indexOf(InputString.get(i).get(0))+1;
		  for(int j=0;j<(InputString.get(i)).size();j++) {
			  output[i][j]=Point.indexOf(InputString.get(i).get(j))+1; 
		  }		  
      }
   }
}

